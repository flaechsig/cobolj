package de.cobolj.nodes;

import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.CobolCallableNode;
import de.cobolj.parser.StartRuleVisitor;


public class ProcedureSectionNode extends StructureNode {
	@Child
	private ParagraphsNode paragraphs;

	public ProcedureSectionNode(String sectionName, ParagraphsNode paragraphs) {
		PARAGRAPH_REGISTRY.put(sectionName.toUpperCase(), this);
		this.paragraphs = paragraphs;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return paragraphs.executeGeneric(frame);
	}

}
