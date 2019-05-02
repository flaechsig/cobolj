package de.cobolj.division.data;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.DataDivisionSectionNode;
import de.cobolj.runtime.Picture;

@NodeInfo(shortName = "LinkageSection")
public class LinkageSectionNode extends DataDivisionSectionNode {
	private final Picture[] dataDescriptionEntries;

	public LinkageSectionNode(List<Picture> dataDescriptionEntryNodes) {
		this.dataDescriptionEntries = dataDescriptionEntryNodes.toArray(new Picture[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Picture last = null;
		for(Picture entry : this.dataDescriptionEntries) {
			getContext().putPicture(frame, entry);
			last = entry;
		}
		return last;
	}

}
