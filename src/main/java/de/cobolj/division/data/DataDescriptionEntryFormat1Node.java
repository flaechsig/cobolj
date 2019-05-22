package de.cobolj.division.data;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.PictureFactory;
import de.cobolj.nodes.PictureNode;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;

@NodeInfo(shortName = "DataDescriptionEntryFormat1")
public class DataDescriptionEntryFormat1Node extends DataDescriptionEntryNode {

	/** Aufgelöster PictureString, d.h. ohne Klammer-Schreibweise */
	private final String pictureString;
	/** Speicherpostion, an dem das Picture für diesen Node startet */
	private int memStart;

	public DataDescriptionEntryFormat1Node(int level, String name, String pictureString, PictureNode dataRedefinesClause,
			DataOccursClause occurs, Object value) {
		super(level, name, dataRedefinesClause, occurs, value);
		this.pictureString = pictureString;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		int occursParent = dataDescParent != null
				? dataDescParent.occurs != null ? dataDescParent.occurs.getOccurs() : 1
				: 1;
		int occursNode = occurs != null ? occurs.getOccurs() : 1;
		for (int j = 1; j <= occursParent; j++) {
			for (int i = 1; i <= occursNode; i++) {
				Integer subscriptParent = occursParent>1?j:null;
				Integer subscriptNode = occursNode>1?i:null;
				Picture pic = createPictre(frame, subscriptParent, subscriptNode);
				if(i==1) {
					memStart = pic.getMemPointer();
				}
			}
		}
		return this;
	}

	private Picture createPictre(VirtualFrame frame, Integer subscriptParent, Integer subscriptNode) {
		PictureGroup parent = null;
		Picture nodePicture = PictureFactory.create(level, name, pictureString);
		if (dataDescParent != null) {
			parent = (PictureGroup) getContext().getPicture(frame, dataDescParent.getQualifiedName()+(subscriptParent!=null?"("+subscriptParent+")":""));
		}
		nodePicture.setParent(parent);
		nodePicture.setSubscript(subscriptNode);
		getContext().putPicture(frame, nodePicture);
		if (value != null) {
			nodePicture.setValue(value);
		}
		return nodePicture;
	}
}
