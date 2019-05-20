package de.cobolj.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.division.data.SubscriptNode;
import de.cobolj.runtime.Picture;

/**
 * Node-Klasse zur Abbildung der Cobol Pictures
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="PictureNode")
public class PictureNode extends ExpressionNode{

	/** Verwaltetes Picture im Node */
	private final String slot;
	
	@Children
	private final SubscriptNode[] subscript;

	public PictureNode(String slot) {
		this(slot, new ArrayList<SubscriptNode>());
	}
	
	public PictureNode(String slot, PictureNode parent) {
		this(slot + (parent!=null?" OF " + parent.getSlot():""), (parent!=null?parent.getSubscripts():new ArrayList<>()));
	}
	
	private List<SubscriptNode> getSubscripts() {
		return Arrays.asList(this.subscript);
	}

	public PictureNode(String slot, List<SubscriptNode> subscript) {
		this.slot = slot;
		this.subscript = subscript.toArray(new SubscriptNode[0]);
	}

	@Override
	public Picture executeGeneric(VirtualFrame frame) {
		List<Number> subsripts = new ArrayList<>();
		for(SubscriptNode node : subscript) {
			subsripts.add(node.executeGeneric(frame));
		}
		return getContext().getPicture(frame, slot, subsripts.toArray(new Number[0]));
	}

	public String getSlot() {
		return slot;
	}
}
