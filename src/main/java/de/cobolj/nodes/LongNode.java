package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class LongNode extends NumberNode {
	long value;

	public LongNode(long value) {
		this.value = value;
	}

	@Override
	public Number executeGeneric(VirtualFrame frame) {
		return value;
	}

	@Override
	public String toString() {
		return Long.toString(value);
	}

}
