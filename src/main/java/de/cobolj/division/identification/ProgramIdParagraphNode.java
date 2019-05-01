package de.cobolj.division.identification;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.LiteralNode;

@NodeInfo(shortName="ProgramIdParagraph")
public class ProgramIdParagraphNode extends CobolNode {
	
	@Child
	private LiteralNode programName;

	public ProgramIdParagraphNode(LiteralNode programName) {
		this.programName = programName;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		getContext().setProgramName(programName.executeGeneric(frame).toString());
		return this;
	}

}
