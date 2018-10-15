package de.cobolj.nodes;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.statements.WriteElementaryItemNode;

@NodeInfo(shortName="Working Storage Section")
public class WorkingStorageSectionNode extends DataDivisionSectionNode {
	@Children
	private final WriteElementaryItemNode[] dataDescriptionEntries;
	

	public WorkingStorageSectionNode(List<WriteElementaryItemNode> dataDescEntry) {
		this.dataDescriptionEntries = dataDescEntry.toArray(new WriteElementaryItemNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for(WriteElementaryItemNode entry : this.dataDescriptionEntries) {
			last = entry.executeGeneric(frame);
		}
		return last;
	}

}
