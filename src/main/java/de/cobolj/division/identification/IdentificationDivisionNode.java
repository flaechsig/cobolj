package de.cobolj.division.identification;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import de.cobolj.nodes.CobolNode;

import java.util.List;

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
		result = programIdParagraph.executeGeneric(frame);
		for( int i=0; i<identificationDivisionBody.length; i++ ) {
			result = identificationDivisionBody[i].executeGeneric(frame);
		}
		return this;
	}

}
