package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

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

	public PictureNode(String slot) {
		this.slot = slot;
	}
	
	@Override
	public Picture executeGeneric(VirtualFrame frame) {
		return getContext().getPicture(frame, slot);
	}

	public String getSlot() {
		return slot;
	}
}
