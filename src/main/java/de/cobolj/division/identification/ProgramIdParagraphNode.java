package de.cobolj.division.identification;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;

@NodeInfo(shortName="ProgramIdParagraph")
public class ProgramIdParagraphNode extends CobolNode {

	private String programName;

	public ProgramIdParagraphNode(String programName) {
		this.programName = programName;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		getContext().setProgramName(programName);
		return this;
	}

}
