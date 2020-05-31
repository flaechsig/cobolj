package de.cobolj.division.data;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PictureFactory;
import de.cobolj.nodes.PictureNode;
import de.cobolj.runtime.Picture;

@NodeInfo(shortName = "DataDescriptionEntryFormat1")
public class DataDescriptionEntryFormat1Node extends DataDescriptionEntryNode {

	/** Aufgelöster PictureString, d.h. ohne Klammer-Schreibweise */
	private final String pictureString;

	public DataDescriptionEntryFormat1Node(int level, String name, String pictureString,
			PictureNode dataRedefinesClause, DataOccursClause occurs, ExpressionNode value) {
		super(level, name, dataRedefinesClause, occurs, value);
		this.pictureString = pictureString;
	}

	@Override
	public Picture executeGeneric(VirtualFrame frame) {
		Picture pic = PictureFactory.create(level, name, pictureString);
		pic.setMemory(mem);
		pic.setParent(parent);
		pic.setSubscript(subscript);	
		if (dataRedefinesClause != null) {
			Picture redefined = dataRedefinesClause.executeGeneric(frame);
			memPointer = redefined.getMemPointer();
			pic.setMemoryPointer(memPointer);
			pic.setRedefined(true);
		} else {
			pic.setMemoryPointer(memPointer);
			if (!pic.isRedefined()) {
				pic.clear();
			}
			if (getValue() != null) {
				pic.setValue(getValue().executeGeneric(frame));
			}
		}

		getContext().putPicture(frame, pic);
		memPointer += pic.getSize();
		
		return pic;
	}
}
