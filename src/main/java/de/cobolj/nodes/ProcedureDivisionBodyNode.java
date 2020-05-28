package de.cobolj.nodes;

import java.util.Collection;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "ProcedureDivisionBody")
public class ProcedureDivisionBodyNode extends CobolNode {
	@Child
	private ParagraphsNode paragraphs;
	@Children
	private final ProcedureSectionNode[] sections;

	public ProcedureDivisionBodyNode(ParagraphsNode paragraphs, List<ProcedureSectionNode> sections) {
		this.paragraphs = paragraphs;
		this.sections = sections.toArray(new ProcedureSectionNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		paragraphs.register();
		for(ProcedureSectionNode section : sections) {
			section.register();
		}

		Object last = null;
		last = paragraphs.executeGeneric(frame);
		for (ProcedureSectionNode section : sections) {
			last = section.executeGeneric(frame);
		}
		return last;
	}

}
