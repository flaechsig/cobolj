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
public class PictureNode extends CobolNode{

	/** Verwaltetes Picture im Node */
	protected Picture picture;

	public PictureNode(Picture picture) {
		this.picture = picture;
	}
	
	public Picture getPicture() {
		return picture;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return picture;
	}
}
