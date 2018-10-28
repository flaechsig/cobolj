package de.cobolj.statement.compute;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.phrase.SizeOverflowException;
import de.cobolj.phrase.SizePhraseNode;
import de.cobolj.runtime.NumericPicture;
import de.cobolj.statements.StatementNode;
import de.cobolj.util.FrameUtil;

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
	private final FrameSlot[] slots;
	/**
	 * Kennzeichen, ob der Store (selber Index wie bei slots) gerundet werden soll
	 */
	private Boolean[] rounded;
	/** Wird ausgeführt, wenn es keine Größenverletzungen gab */
	@Child
	private SizePhraseNode success;
	/** Wird ausgeführt, wenn es eine Größenverletzung gab. */
	@Child
	private SizePhraseNode error;

	public ComputeStatementNode(ExpressionNode arithmeticEx, List<FrameSlot> slots, List<Boolean> rounded,
			SizePhraseNode successPhrase, SizePhraseNode errorPhrase) {
		this.arithmetic = arithmeticEx;
		this.slots = slots.toArray(new FrameSlot[] {});
		this.rounded = rounded.toArray(new Boolean[] {});
		this.success = successPhrase;
		this.error = errorPhrase;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		BigDecimal arithmeticResult = (BigDecimal) arithmetic.executeGeneric(frame);

		boolean hasSizeError = false;
		for (int i = 0; i < slots.length; i++) {
			FrameSlot slot = slots[i];
			boolean toRound = this.rounded[i];
			NumericPicture picture = FrameUtil.getNumericPicture(frame, slot);
			BigDecimal value = arithmeticResult;
			if (toRound) {
				value = value.setScale(picture.getScale(), RoundingMode.HALF_UP);
			}
			try {
				picture.setValue(value.setScale(picture.getScale(), RoundingMode.FLOOR), checkSizeError());
			} catch (SizeOverflowException e) {
				hasSizeError = true;
			}
		}
		if (hasSizeError && error != null) {
			error.executeGeneric(frame);
		} else if(!hasSizeError && success!= null) {
			success.executeGeneric(frame);
		}
		return null;
	}

	private boolean checkSizeError() {
		return success != null || error != null;
	}
}