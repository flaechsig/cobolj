package de.cobolj.division.data;

import static de.cobolj.nodes.NodeHelper.excecuteGeneric;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.DataDivisionSectionNode;
import de.cobolj.runtime.Picture;

@NodeInfo(shortName = "LinkageSection")
public class LinkageSectionNode extends DataDivisionSectionNode {
	@Children
	private final DataDescriptionEntryNode[] dataDescriptionEntries;

	public LinkageSectionNode(List<DataDescriptionEntryNode> dataDescriptionEntryNodes) {
		this.dataDescriptionEntries = dataDescriptionEntryNodes.toArray(new DataDescriptionEntryNode[0]);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {

		List<Picture> rootLevelPictures = DataDescriptionEntryNode.buildPictureListTree(dataDescriptionEntries,0);
		for (Picture pic : rootLevelPictures) {
			addToStorage(frame, pic);
		}
		
		return this;
	}

}
