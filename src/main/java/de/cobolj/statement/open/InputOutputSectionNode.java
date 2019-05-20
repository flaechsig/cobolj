package de.cobolj.statement.open;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.division.data.InputOutputSectionParagraphNode;
import de.cobolj.nodes.CobolNode;

@NodeInfo(shortName="InputOutputSecion")
public class InputOutputSectionNode extends CobolNode {

	@Children
	private final InputOutputSectionParagraphNode[] paragraphs;

	public InputOutputSectionNode(List<InputOutputSectionParagraphNode> paragraphs) {
		this.paragraphs = paragraphs.toArray(new InputOutputSectionParagraphNode[0]);
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
