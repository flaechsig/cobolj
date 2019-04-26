package de.cobolj.parser.statement.perform;

import java.math.BigDecimal;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.runtime.NumericPicture;

/**
 * PERFORM AFTER. Mit diesem Konstrukt werden verschachtelte Schleifen
 * abgebildet.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "PerformAfter")
public class PerformAfterNode extends ExpressionNode {
	private final boolean testBefore;
	private String var;
	@Child
	private ExpressionNode perform;
	@Child
	private ExpressionNode condition;
	@Child
	private ExpressionNode start;
	@Child
	private ExpressionNode step;

	public PerformAfterNode(boolean testBefore, ExpressionNode condition, ExpressionNode perform, String var,
			ExpressionNode start, ExpressionNode step) {
		this.perform = perform;
		this.testBefore = testBefore;
		this.condition = condition;
		this.var = var;
		this.start = start;
		this.step = step;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		NumericPicture picture = (NumericPicture) getContext().getPicture(frame, var);
		picture.setValue(start.executeGeneric(frame));
		BigDecimal stepWidht = BigDecimal.valueOf((long) step.executeGeneric(frame));

		while (true) {
			boolean conditionResult = (boolean) condition.executeGeneric(frame);
			if (testBefore && conditionResult) {
				picture.setValue(start.executeGeneric(frame));
				break;
			}
			perform.executeGeneric(frame);
			if (!testBefore && conditionResult) {
				break;
			}
			picture.setValue(picture.getBigDecimal().add(stepWidht));
		}
		return null;
	}

}
