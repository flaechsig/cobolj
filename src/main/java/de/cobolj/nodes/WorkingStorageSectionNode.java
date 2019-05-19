package de.cobolj.nodes;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.division.data.DataDescriptionEntryNode;
import de.cobolj.runtime.Picture;

@NodeInfo(shortName = "WorkingStorageSection")
public class WorkingStorageSectionNode extends DataDivisionSectionNode {
	@Children
	private final DataDescriptionEntryNode[] entries;

	public WorkingStorageSectionNode(List<DataDescriptionEntryNode> dataDescriptionEntryNodes) {
		this.entries = dataDescriptionEntryNodes.toArray(new DataDescriptionEntryNode[0]);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		DataDescriptionEntryNode.buildHierarchie(entries);
		Map<String, Object> values = new HashedMap<>();
		for(DataDescriptionEntryNode node : entries) {
			values.put(node.getQualifiedName(), node.getValue());
		}
		
		// FIXME: Es sieht falsch aus, dass nicht über die executeGeneric-Methode gegangen wird
		List<Picture> rootLevelPictures = DataDescriptionEntryNode.buildPictureListTree(entries,0);
		for (Picture pic : rootLevelPictures) {
			addToStorage(frame, pic, values);
		}
		return this;
	}
}
