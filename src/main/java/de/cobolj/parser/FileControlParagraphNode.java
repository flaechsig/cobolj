package de.cobolj.parser;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName="FileControlParagraph")
public class FileControlParagraphNode extends InputOutputSectionParagraphNode {

	@Children
	private final FileControlEntryNode[] entries;

	public FileControlParagraphNode(List<FileControlEntryNode> entries2) {
		this.entries = entries2.toArray(new FileControlEntryNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for(FileControlEntryNode node : entries) {
			last = node.executeGeneric(frame);
		}
		return last;
	}

}
