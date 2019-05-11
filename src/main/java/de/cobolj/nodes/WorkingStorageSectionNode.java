package de.cobolj.nodes;

import static de.cobolj.nodes.NodeHelper.excecuteGeneric;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.division.data.DataDescriptionEntryNode;

@NodeInfo(shortName="WorkingStorageSection")
public class WorkingStorageSectionNode extends DataDivisionSectionNode {
	@Children
	private final DataDescriptionEntryNode[] dataDescriptionEntries;
	

	public WorkingStorageSectionNode(List<DataDescriptionEntryNode> dataDescriptionEntryNodes) {
		this.dataDescriptionEntries = dataDescriptionEntryNodes.toArray(new DataDescriptionEntryNode[0]);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object result = null;
		result = excecuteGeneric(dataDescriptionEntries, result, frame);
		return result;
	}

}
