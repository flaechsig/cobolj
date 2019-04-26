package de.cobolj.statement.perform;

import java.math.BigDecimal;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.runtime.NumericPicture;

@NodeInfo(shortName = "PerformVarying")
public class PerformVaryingExpressionNode extends ExpressionNode {
	/**
	 * Kennzeichen, ob die Schleifenbedingung vor dem Schleifen-Body geprüft werden
	 * soll
	 */
	private final boolean testBefore;
	/** Schleifenzähler */
	@Child
	private ExpressionNode condition;
	/** Schleifenzähler */
	private final FrameSlot var;
	/** Startwert */
	@Child
	private ExpressionNode start;
	/** Schrittweite */
	@Child
	private ExpressionNode step;
	@Child
	private ExpressionNode perform;

	public PerformVaryingExpressionNode(boolean testBefore, ExpressionNode condition, ExpressionNode perform,
			FrameSlot var, ExpressionNode start, ExpressionNode step) {
		this.perform = perform;
		this.testBefore = testBefore;
		this.condition = condition;
		this.var = var;
		this.start = start;
		this.step = step;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
//		NumericPicture picture= FrameUtil.getNumericPicture(frame, var);
		NumericPicture picture = (NumericPicture) getContext().getPicture(var);
		picture.setValue(start.executeGeneric(frame));
		BigDecimal stepWidht = BigDecimal.valueOf((long) step.executeGeneric(frame));
		
		while(true) {
			boolean conditionResult = (boolean) condition.executeGeneric(frame);
			if(testBefore && conditionResult) {
				break;
			}
			perform.executeGeneric(frame);
			if(!testBefore && conditionResult) {
				break;
			}
			picture.setValue(picture.getBigDecimal().add(stepWidht));
		}

		return null;
	}
}
