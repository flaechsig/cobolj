package de.cobolj.division.data;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.DataDivisionSectionNode;

@NodeInfo(shortName = "LinkageSection")
public class LinkageSectionNode extends DataDivisionSectionNode {
	@Children
	private final DataDescriptionEntryNode[] dataDescriptionEntries;

	public LinkageSectionNode(List<DataDescriptionEntryNode> dataDescriptionEntryNodes) {
		this.dataDescriptionEntries = dataDescriptionEntryNodes.toArray(new DataDescriptionEntryNode[0]);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		DataDescriptionEntryNode.buildHierarchie(dataDescriptionEntries);
		for(DataDescriptionEntryNode node : dataDescriptionEntries) {
			node.executeGeneric(frame);
		}
		return this;
	}

}
