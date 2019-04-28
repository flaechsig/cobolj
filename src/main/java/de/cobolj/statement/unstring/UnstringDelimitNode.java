package de.cobolj.statement.unstring;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.ExpressionNode;

@NodeInfo(shortName = "UnstringDelimit")
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
	
	/**
	 * Ermittelt die nächste Endposition anhand des Delimiters.
	 * 
	 * @param quelle
	 * @param startPos
	 * @return
	 */
	public int nextEndPosition(String quelle, int startPos) {
		int idx = quelle.indexOf(delim, startPos);
		if(idx == -1) {
			idx = quelle.length();
		}
		return idx;
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
