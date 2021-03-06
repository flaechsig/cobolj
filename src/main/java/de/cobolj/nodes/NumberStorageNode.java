package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.runtime.NumericPicture;

@NodeInfo(shortName="NumberStorage")
public class NumberStorageNode extends NumberNode {
	
	@Child
	private PictureNode item;

	public NumberStorageNode(PictureNode item) {
		this.item = item;
	}
	
	@Override
	public Number executeGeneric(VirtualFrame frame) {
		return ((NumericPicture)item.executeGeneric(frame)).getBigDecimal();
	}

}
