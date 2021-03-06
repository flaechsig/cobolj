package de.cobolj.statement.compute;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.phrase.PhraseNode;
import de.cobolj.phrase.SizeOverflowException;
import de.cobolj.runtime.NumericPicture;
import de.cobolj.statement.StatementNode;

/**
 * Implementierung des COMPUTE Statement.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "Compute")
public class ComputeStatementNode extends StatementNode {

	/** Arithmetischer Ausdruck, der Ausgewertet wird. */
	@Child
	private ExpressionNode arithmetic;
	/** Storage, in den das Ergebnis geschrieben wird */
	@Children
	private final PictureNode[] slots;
	/**
	 * Kennzeichen, ob der Store (selber Index wie bei slots) gerundet werden soll
	 */
	private Boolean[] rounded;
	/** Wird ausgeführt, wenn es keine Größenverletzungen gab */
	@Child
	private PhraseNode success;
	/** Wird ausgeführt, wenn es eine Größenverletzung gab. */
	@Child
	private PhraseNode error;

	public ComputeStatementNode(ExpressionNode arithmeticEx, List<PictureNode> slots, List<Boolean> rounded,
			PhraseNode successPhrase, PhraseNode errorPhrase) {
		this.arithmetic = arithmeticEx;
		this.slots = slots.toArray(new PictureNode[] {});
		this.rounded = rounded.toArray(new Boolean[] {});
		this.success = successPhrase;
		this.error = errorPhrase;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object lastCall = null;
		BigDecimal arithmeticResult = (BigDecimal) arithmetic.executeGeneric(frame);

		boolean hasSizeError = false;
		for (int i = 0; i < slots.length; i++) {
			PictureNode slot = slots[i];
			boolean toRound = this.rounded[i];
			NumericPicture picture = (NumericPicture)slot.executeGeneric(frame);
			BigDecimal value = arithmeticResult;
			if (toRound) {
				value = value.setScale(picture.getScale(), RoundingMode.HALF_UP);
			}
			try {
				picture.setValue(value.setScale(picture.getScale(), RoundingMode.FLOOR), checkSizeError());
			} catch (SizeOverflowException e) {
				hasSizeError = true;
			}
			lastCall = picture;
		}
		if (hasSizeError && error != null) {
			 lastCall = error.executeGeneric(frame);
		} 
		if(!hasSizeError && success!= null) {
			 lastCall = success.executeGeneric(frame);
		}
		return lastCall;
	}

	private boolean checkSizeError() {
		return success != null || error != null;
	}
}