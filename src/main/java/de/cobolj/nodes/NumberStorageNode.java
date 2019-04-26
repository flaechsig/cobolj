package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.runtime.NumericPicture;

@NodeInfo(shortName="NumberStorage")
public class NumberStorageNode extends NumberNode {
	
	private final String item;

	public NumberStorageNode(String item) {
		this.item = item;
	}
	
	@Override
	public Number executeGeneric(VirtualFrame frame) {
		return ((NumericPicture)getContext().getPicture(frame, item)).getBigDecimal();
	}

}
