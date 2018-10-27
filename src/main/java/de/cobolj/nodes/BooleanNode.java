package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class BooleanNode extends NumberNode {
	boolean value;

	public BooleanNode(boolean value) {
		this.value = value;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return value;
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}
}
