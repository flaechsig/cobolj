package de.cobolj.parser.statement.add;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.phrase.SizeOverflowException;
import de.cobolj.runtime.NumericPicture;

/**
 * Repräsentiert die möglichen mathematischen Implementierungen
 * 
 * @author flaechsig
 *
 */
public abstract class MathImplNode extends CobolNode {
	/** Kennzeichen, ob SIZE-ERRORs behandelt werden */
	protected boolean sizeErrorCheck;
	/** Kennzeichen, ob der n-te FrameSlot kaufmännisch gerundet werden soll */
	protected Boolean[] rounded;
	/**
	 * Linker Teil des Ausdrucks. Dieser wird summiert und wirkt sich auf den
	 * rechten Teil (From/To/Giving) aus.
	 */
	@Children
	protected final ExpressionNode[] left;
	/**
	 * Mittlerer Teil des Ausdrucks. Dieser kann From/To sein und wird nur genutzt,
	 * wenn Giving angegeben ist.
	 */
	@Child
	protected ExpressionNode right = null;
	/**
	 * Rechter Teil des Ausdrucks. Auf diesen Teil wirkt der linke Ausdruck. Er wird
	 * also um den linken Teil erweitert/reduziert/ersetzt
	 */
	@Children
	protected final PictureNode[] result;

	/**
	 * Unterstütz Ausdrücke mit zweit Operanden und mehreren Ergebnisspeichern.
	 * 
	 * @param left    Linker Ausdruck. Dies kann ein Literal oder ein Identifier
	 *                sein.
	 * @param right   rechter Ausdruck. Dies ist ein Literal oder Identifier.
	 * @param results Liste von Ergebnisspeichern
	 * @param rounded Liste von Rundungsanweisungen für den Ergenisspeicher
	 */
	public MathImplNode(List<ExpressionNode> left, ExpressionNode right, List<PictureNode> results,
			List<Boolean> rounded) {
		this.left = left.toArray(new ExpressionNode[] {});
		this.right = right;
		this.result = results.toArray(new PictureNode[] {});
		this.rounded = rounded.toArray(new Boolean[] {});
	}

	/**
	 * Unterstützt Ausdrücke mit zwei Parameterbereichen. In diesem Fall ist
	 * rightResult einerseits der Operand, sowie der Ergebnisspeicher.
	 * 
	 * @param left        Linker Operand. Dies kann ein Literal oder Identifier sein
	 * @param rightResult Liste rechter Operand und Ergebnisspeicher
	 * @param rounded     Liste von Rundungsanweisungen für den Ergenisspeicher
	 */
	public MathImplNode(List<ExpressionNode> left, List<PictureNode> rightResult, List<Boolean> rounded) {
		this.left = left.toArray(new ExpressionNode[] {});
		this.right = null;
		this.result = rightResult.toArray(new PictureNode[] {});
		this.rounded = rounded.toArray(new Boolean[] {});
	}

	/**
	 * Implementiert die mathematische Funktion für die Rechenoperation
	 * 
	 * Bsp:
	 * <ul>
	 * <li>ADD A TO B entspricht B = A+B bzw.
	 * <li>ADD A TO B GIVING B
	 * <li>ADD A TO B GIVING C entspricht C = A + B
	 * <li>SUBTRACT A FROM B entspricht B = B-A
	 * <li>SUBTRACT A FROM B GIVING C entspricht C= B-A
	 * </ul>
	 * 
	 * @param left    linker Teil des Ausdrucks
	 * @param rechter Teil des Ausdrucks. Dies können mehrere Variablen sein. Je
	 *                nach Ausdruck wird die Operation auf jede rechte Variable
	 *                ausgeführt.
	 * @return Ergebnis der Rechenergebnisses. Dieses kann wiederum mehrere
	 *         Ergebnisse haben.
	 * 
	 */
	protected abstract List<BigDecimal> calculateNewValue(List<BigDecimal> left, BigDecimal right,
			List<BigDecimal> rightResult);

	/**
	 * Führt die Berechnung für die verschiedenen mathematischen Funktionen aus. Die
	 * wesentliche Berechnung wird hierbei in Unterklassen realisiert, die bspw. die
	 * verschieden Formen der Addition implementieren.
	 * 
	 * An dieser Stelle werden die Operanden aufbereitet und and die konkrete
	 * Implementierung weitergereicht, die im Anschluss in die Ergebnisspeicher
	 * übertragen wird.
	 */
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		
		// Ergebnisliste bzw "rightResult" ermitteln
		List<BigDecimal> leftOperands = new ArrayList<>();
		for (ExpressionNode node : left) {
			leftOperands.add(valueOf(frame, node));
		}

		// Wert aus dem rechten Teil ermitteln (falls gesetzt)
		BigDecimal rightValue = BigDecimal.ZERO;
		if (right != null) {
			rightValue = valueOf(frame, right);
		}

		// Ergebnisliste bzw "rightResult" ermitteln
		List<BigDecimal> rightResult = new ArrayList<>();
		for (int i = 0; i < result.length; i++) {
			rightResult.add(((NumericPicture)result[i].executeGeneric(frame)).getBigDecimal());
		}

		// Eigentliche Berechnung in Sub-Klassen ausgelagert
		List<BigDecimal> results = calculateNewValue(leftOperands, rightValue, rightResult); 
		
		// Ergebnisse in das Result übertragen (in den Ergenbisspeicher)
		boolean hasSizeError = false;
		int i=0;
		for (BigDecimal value : results) {
			PictureNode slot = result[i];
			boolean toRound = this.rounded[i];
			NumericPicture picture = (NumericPicture)slot.executeGeneric(frame);
			if (toRound) {
				value = value.setScale(picture.getScale(), RoundingMode.HALF_UP);
			}
			try {
				picture.setValue(value.setScale(picture.getScale(), RoundingMode.FLOOR), this.sizeErrorCheck);
			} catch (SizeOverflowException e) {
				hasSizeError = true;
			}
			i++;
		}
		if (hasSizeError) {
			throw new SizeOverflowException();
		}
		return this;
	}

	/**
	 * Setzt das Kennzeichen, ob ein Size-Check durchgeführt werden soll.
	 * 
	 * @param sizeCheck
	 */
	public void setSizeErrorCheck(boolean sizeCheck) {
		this.sizeErrorCheck = sizeCheck;
	}
}
