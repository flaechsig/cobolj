package de.cobolj.division.data;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ArithmeticNode;
import de.cobolj.nodes.CobolNode;

@NodeInfo(shortName="Subscript")
public class SubscriptNode extends CobolNode {
	@Child
	ArithmeticNode subscriptDescription; 
	
	public SubscriptNode(ArithmeticNode subscriptDescription) {
		this.subscriptDescription = subscriptDescription;
	}

	@Override
	public Number executeGeneric(VirtualFrame frame) {
		return subscriptDescription.executeGeneric(frame);
	}

}
