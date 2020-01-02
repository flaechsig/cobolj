package de.cobolj.division.data;

import java.util.Arrays;

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
	
	public DataDescriptionEntryFormat1Node(int level, String name, String pictureString,
			PictureNode dataRedefinesClause, DataOccursClause occurs, Object value) {
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
				Integer subscriptParent = occursParent > 1 ? j : null;
				Integer subscriptNode = occursNode > 1 ? i : null;
				Picture pic = createPictre(frame, subscriptParent, subscriptNode);
				if (i == 1) {
					pic.getMemPointer();
				}
			}
		}
		return this;
	}

	private Picture createPictre(VirtualFrame frame, Integer subscriptParent, Integer subscriptNode) {
		PictureGroup parent = null;
		Picture nodePicture = PictureFactory.create(level, name, pictureString);
		nodePicture.setSubscript(subscriptNode);
		if (dataDescParent != null) {
			parent = (PictureGroup) getContext().getPicture(frame,
					dataDescParent.getQualifiedName() + (subscriptParent != null ? "(" + subscriptParent + ")" : ""));
			nodePicture.setParent(parent);
		}

		if (dataRedefinesClause != null) {
			// Dieses Element redefiniert direkt ein anderes Element
			// Daher ergibt sich der Speicherplatz aus dem referenzierten Element
			Picture redefines = dataRedefinesClause.executeGeneric(frame);
			nodePicture.setMemoryPointer(redefines.getMemPointer());
			nodePicture.setMemory(redefines.getMemory());
			nodePicture.setRedefined(true);
		} else {
			// Die Position ergibt sich aus dem Vorgänger
			if (dataDescPresessor == null) {
				if (parent != null) {
					int index = subscriptNode==null?0:subscriptNode-1;
					nodePicture.setMemoryPointer(parent.getMemPointer()+(nodePicture.getSize()*index));
					nodePicture.setMemory(parent.getMemory());
				} else {
					byte[] mem = Arrays.copyOf(new byte[] {0}, 1000);
					nodePicture.setMemoryPointer(0);
					nodePicture.setMemory(mem);
				}
			} else {
				DataOccursClause occurs = dataDescPresessor.getOccurs();
				Picture presessor = getContext().getPicture(frame,
						dataDescPresessor.getQualifiedName() + (occurs != null ? "(" + occurs.getOccurs() + ")" : ""));
				int index = subscriptNode==null?0:subscriptNode-1;
				nodePicture.setMemoryPointer(presessor.getMemPointer() + presessor.getSize() + (nodePicture.getSize()*index));
				nodePicture.setMemory(presessor.getMemory());
			}
			if(!nodePicture.isRedefined()) {
				nodePicture.clear();
			}
		}

		getContext().putPicture(frame, nodePicture);
		if (value != null) {
			nodePicture.setValue(value);
		} 
		return nodePicture;
	}
}
