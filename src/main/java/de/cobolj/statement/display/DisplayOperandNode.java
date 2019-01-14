package de.cobolj.statement.display;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.ExpressionNode;

@NodeInfo(shortName="DisplayOperand")
public class DisplayOperandNode extends CobolNode {
	@Child
	private ExpressionNode operand;
	
	public DisplayOperandNode(ExpressionNode operand) {
		this.operand = operand;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return operand.executeGeneric(frame);	 
	}

}
