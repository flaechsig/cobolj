package de.cobolj.statement.open;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.parser.EnvironmentDivisionBodyNode;
import de.cobolj.parser.InputOutputSectionParagraphNode;

@NodeInfo(shortName="InputOutputSecion")
public class InputOutputSecionNode extends EnvironmentDivisionBodyNode {

	@Children
	private final InputOutputSectionParagraphNode[] paragraphs;

	public InputOutputSecionNode(List<InputOutputSectionParagraphNode> paragraphs) {
		this.paragraphs = paragraphs.toArray(new InputOutputSectionParagraphNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for(InputOutputSectionParagraphNode node : paragraphs) {
			last = node.executeGeneric(frame);
		}
		return last;
	}

}
