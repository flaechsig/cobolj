package de.cobolj.nodes;

import java.math.BigDecimal;

import com.oracle.truffle.api.frame.VirtualFrame;

public class BigDecimalNode extends NumberNode {
	private BigDecimal value;

	public BigDecimalNode(BigDecimal value) {
		this.value = value;
	}

	@Override
	public Number executeGeneric(VirtualFrame frame) {
		return value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

}
