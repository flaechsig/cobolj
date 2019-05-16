package de.cobolj.nodes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.division.data.DataDescriptionEntryNode;
import de.cobolj.division.data.DataOccursClause;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;

@NodeInfo(shortName = "WorkingStorageSection")
public class WorkingStorageSectionNode extends DataDivisionSectionNode {
	@Children
	private final DataDescriptionEntryNode[] entries;

	public WorkingStorageSectionNode(List<DataDescriptionEntryNode> dataDescriptionEntryNodes) {
		this.entries = dataDescriptionEntryNodes.toArray(new DataDescriptionEntryNode[0]);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		List<Picture> rootLevelPictures = DataDescriptionEntryNode.buildPictureListTree(entries,0);
		for (Picture pic : rootLevelPictures) {
			addToStorage(frame, pic);
		}
		return this;
	}
}
