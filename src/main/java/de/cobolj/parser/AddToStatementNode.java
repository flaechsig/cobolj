package de.cobolj.parser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.ExpressionNode;
import de.cobolj.phrase.SizeOverflowException;
import de.cobolj.runtime.NumericPicture;

/**
 * Führt die Berechnung der Add-Poperation im Muster ADD op1 op2 TO op3 op4
 * durch. Es wird beim execute null zurückgeliefert.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "AddToStatement")
public class AddToStatementNode extends AddImplNode {
	/** Liste der Summanden, die auf die To-Clause angewendet werden. */
	private final ExpressionNode[] addFrom;
	/** Liste der ElementItem, die durch die Operation bearbeitet werden. */
	private final FrameSlot[] addTo;
	/** Kennzeichen, ob der n-te FrameSlot kaufmännisch gerundet werden soll */
	private Boolean[] rounded;

	public void setSizeErrorCheck(boolean sizeErrorCheck) {
		this.sizeErrorCheck = sizeErrorCheck;
	}

	public AddToStatementNode(List<ExpressionNode> summands, List<FrameSlot> results, List<Boolean> rounded) {
		this.addFrom = summands.toArray(new ExpressionNode[] {});
		this.addTo = results.toArray(new FrameSlot[] {});
		this.rounded = rounded.toArray(new Boolean[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		// Summe über alle Summanden (vorderer Teil) bilden
		BigDecimal sum = BigDecimal.ZERO;
		for (CobolNode node : addFrom) {
			Object value = node.executeGeneric(frame);
			if (value instanceof Long) {
				sum = sum.add(BigDecimal.valueOf((long) value));
			} else if (value instanceof BigDecimal) {
				sum = sum.add((BigDecimal) value);
			} else if (value instanceof NumericPicture) {
				NumericPicture pic = (NumericPicture) value;
				sum = sum.add((BigDecimal) pic.getValue());
			} else {
				throw new RuntimeException("Unerwarteter Datentyp " + value.getClass().getSimpleName());
			}
		}
		// sum enthält jetzt den linken Teil des Ausdrucks
		boolean hasSizeError = false;
		for (int i = 0; i < addTo.length; i++) {
			FrameSlot slot = addTo[i];
			boolean toRound = this.rounded[i];
			try {
				NumericPicture picture = (NumericPicture) frame.getObject(slot);
				BigDecimal value = picture.getBigDecimal();
				value = calculateNewValue(sum, value);
				if (toRound) {
					value = value.setScale(picture.getScale(), RoundingMode.HALF_UP);
				}
				try {
					picture.setValue(value.setScale(picture.getScale(), RoundingMode.FLOOR), this.sizeErrorCheck);
				} catch (SizeOverflowException e) {
					hasSizeError = true;
				}
			} catch (FrameSlotTypeException e) {
				throw new RuntimeException("Unerwarteteer Fehler", e);
			}
		}
		if (hasSizeError) {
			throw new SizeOverflowException();
		}
		return null;
	}

	protected BigDecimal calculateNewValue(BigDecimal sum, BigDecimal value) {
		return value.add(sum);
	}

}
