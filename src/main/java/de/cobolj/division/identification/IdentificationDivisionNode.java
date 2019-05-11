package de.cobolj.division.identification;

import static de.cobolj.nodes.NodeHelper.excecuteGeneric;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;

@NodeInfo(shortName="IdentificationDivision")
public class IdentificationDivisionNode extends CobolNode {
	@Child
	private ProgramIdParagraphNode programIdParagraph;
	@Children
	private final IdentificationDivisionBodyNode[] identificationDivisionBody;

	public IdentificationDivisionNode(ProgramIdParagraphNode programIdParagraph, List<IdentificationDivisionBodyNode> identificationDivisionBody) {
		this.programIdParagraph = programIdParagraph;
		this.identificationDivisionBody = identificationDivisionBody.toArray(new IdentificationDivisionBodyNode[0]);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object result = null;
		result = excecuteGeneric(programIdParagraph, result, frame);
		result = excecuteGeneric(identificationDivisionBody, result, frame);
		return this;
	}

}
