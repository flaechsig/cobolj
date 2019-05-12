package de.cobolj.division.data;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.nodes.ArithmeticNode;

public class IntegerNode extends ArithmeticNode {
	private final Integer number;

	public IntegerNode(Integer number) {
		this.number = number;
	}

	@Override
	public Integer executeGeneric(VirtualFrame frame) {
		return number;
	}

}
