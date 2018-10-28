package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class NumberNode extends LiteralNode {
	@Override
	public abstract Number executeGeneric(VirtualFrame frame);
}
