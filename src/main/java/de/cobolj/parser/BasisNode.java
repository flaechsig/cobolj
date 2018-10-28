package de.cobolj.parser;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ArithmeticNode;
import de.cobolj.nodes.NumberNode;

@NodeInfo(shortName="Basis")
public class BasisNode extends ArithmeticNode {
	@Child
	private NumberNode child;
	
	public BasisNode(NumberNode child) {
		this.child = child;
	}
	
	@Override
	public Number executeGeneric(VirtualFrame frame) {
		return child.executeGeneric(frame);
	}

}
