package de.cobolj.statements.perform;

import java.math.BigDecimal;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.runtime.NumericPicture;

@NodeInfo(shortName = "PerformVarying")
public class PerformVaryingNode extends PerformUntilNode {
	/** Schleifenzähler */
	private final FrameSlot var;
	/** Startwert */
	@Child
	private ExpressionNode start;
	/** Schrittweite */
	@Child
	private ExpressionNode step;

	public PerformVaryingNode(boolean testBefore, ExpressionNode condition, PerformStatementNode perform,
			FrameSlot var, ExpressionNode start, ExpressionNode step) {
		super(testBefore, condition, perform);
		this.var = var;
		this.start = start;
		this.step = step;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		NumericPicture picture;
		try {
			picture = (NumericPicture) frame.getObject(var);
		} catch (FrameSlotTypeException e) {
			throw new RuntimeException(e);
		}
		picture.setValue(start.executeGeneric(frame));
		BigDecimal stepWidht = BigDecimal.valueOf((long) step.executeGeneric(frame));
		
		while(true) {
			boolean conditionResult = (boolean) until.executeGeneric(frame);
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
