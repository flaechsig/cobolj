package de.cobolj.parser;

import java.io.File;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;

@NodeInfo(shortName="FileControlEntry")
public class FileControlEntryNode extends CobolNode {

	private String fileName;
	@Child
	private AssignClauseNode assign;

	public FileControlEntryNode(String fileName, AssignClauseNode assign) {
		this.fileName = fileName;
		this.assign = assign;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		File file = assign.executeGeneric(frame);
		getContext().getFileDescriptorByName(fileName).setFile(file);
		return this;
	}

}
