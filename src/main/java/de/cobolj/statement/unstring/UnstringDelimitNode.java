package de.cobolj.statement.unstring;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.ExpressionNode;

public class UnstringDelimitNode extends CobolNode {
	private boolean all = false;
	@Child
	private ExpressionNode delimiter;
	
	// Nach der Ausführtung von excecuteGerneric verfügbar
	String delim; 

	public UnstringDelimitNode(ExpressionNode identifier, boolean all) {
		this.delimiter = identifier;
		this.all = all;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		delim = delimiter.executeGeneric(frame).toString();
		return null;
	}

	public int getSize() {
		return delim.length();
	}

	public boolean isAll() {
		return all;
	}

	public String getDelim() {
		return delim;
	}

}
