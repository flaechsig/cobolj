package de.cobolj.division.identification;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;

@NodeInfo(shortName="IdentificationDivision")
public class IdentificationDivisionNode extends CobolNode {
	@Child
	private ProgramIdParagraphNode programIdParagraph;

	public IdentificationDivisionNode(ProgramIdParagraphNode programIdParagraph) {
		this.programIdParagraph = programIdParagraph;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		programIdParagraph.executeGeneric(frame);
		return this;
	}

}
