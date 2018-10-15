package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/** String-Repräsentation im AST */
@NodeInfo(shortName="String")
public class StringNode extends LiteralNode {
	private String value;
	
	public StringNode(String value) {
		this.value = value;
	}
	
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}

}
