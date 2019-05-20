package de.cobolj.division.data;

import javax.management.Descriptor;

import org.apache.commons.lang3.ObjectUtils;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.PictureFactory;
import de.cobolj.nodes.PictureNode;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;

@NodeInfo(shortName = "DataDescriptionEntryFormat1")
public class DataDescriptionEntryFormat1Node extends DataDescriptionEntryNode {

	private final String pictureString;

	public DataDescriptionEntryFormat1Node(Picture picture, String pictureString, PictureNode dataRedefinesClause,
			DataOccursClause occurs, Object value) {
		super(picture, dataRedefinesClause, occurs, value);
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
				createPictre(frame, subscriptParent, subscriptNode);
			}
		}
		return this;
	}

	private void createPictre(VirtualFrame frame, Integer subscriptParent, Integer subscriptNode) {
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
	}
}
