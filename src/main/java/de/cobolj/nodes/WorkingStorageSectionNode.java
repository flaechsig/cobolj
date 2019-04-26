package de.cobolj.nodes;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.runtime.Picture;

@NodeInfo(shortName="WorkingStorageSection")
public class WorkingStorageSectionNode extends DataDivisionSectionNode {
	private final Picture[] dataDescriptionEntries;
	

	public WorkingStorageSectionNode(List<Picture> dataDescEntry) {
		this.dataDescriptionEntries = dataDescEntry.toArray(new Picture[] {});
	}

	@Override
	public Picture executeGeneric(VirtualFrame frame) {
		Picture last = null;
		for(Picture entry : this.dataDescriptionEntries) {
			getContext().putPicture(frame, entry);
			last = entry;
		}
		return last;
	}

}
