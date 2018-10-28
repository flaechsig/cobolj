package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class ArithmeticNode extends ExpressionNode {
	@Override
	public abstract Number executeGeneric(VirtualFrame frame);

}
