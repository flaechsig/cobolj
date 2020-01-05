package de.cobolj.nodes;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.division.data.DataDescriptionEntryNode;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;

@NodeInfo(shortName = "WorkingStorageSection")
public class WorkingStorageSectionNode extends DataDivisionSectionNode {
	@Children
	private final DataDescriptionEntryNode[] entries;

	public WorkingStorageSectionNode(List<DataDescriptionEntryNode> dataDescriptionEntryNodes) {
		this.entries = dataDescriptionEntryNodes.toArray(new DataDescriptionEntryNode[0]);
	}

	public Object executeGeneric(VirtualFrame frame) {
		AtomicInteger pos = new AtomicInteger(0);
		byte[] mem = new byte[10000];

		DataDescriptionEntryNode.setMem(mem);
		DataDescriptionEntryNode.setMemPointer(0);
		while (pos.get() < entries.length) {
			initializeMemory(frame, null, pos);
		}

		return this;
	}

	private void initializeMemory(VirtualFrame frame, PictureGroup parent, AtomicInteger pos) {
		DataDescriptionEntryNode act = entries[pos.get()];
		DataDescriptionEntryNode next = null;
		Picture pic = null;
		
		if (pos.get() + 1 < entries.length) {
			next = entries[pos.get() + 1];
		}
		
		int occurs = act.getOccurs() == null ? 1 : act.getOccurs().getOccurs();
		int oldPos = pos.get();
		for (int i = 1; i <= occurs; i++) {
			act.setParent(parent);
			act.setSubscript(act.getOccurs()==null?null:i);
			pic = (Picture) act.executeGeneric(frame);
			
			pos.set(pos.get() + 1);
			
			while (pos.get() < entries.length && next.getLevel() > act.getLevel()) {
				PictureGroup group = (PictureGroup) pic;
				initializeMemory(frame, group, pos);
				if (pos.get() < entries.length) {
					next = entries[pos.get()];
				}
			}
			
			// Pos zurücksetzen, wenn Occurs noch nicht abgearbeitet ist
			if(act.getOccurs() != null && i < occurs) {
				pos.set(oldPos);
			}
		}
	}
}
