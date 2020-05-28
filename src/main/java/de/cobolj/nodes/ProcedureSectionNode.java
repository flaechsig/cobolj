package de.cobolj.nodes;

import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.CobolCallableNode;
import de.cobolj.parser.StartRuleVisitor;


public class ProcedureSectionNode extends StructureNode {
	private final String name;
	@Child
	private ParagraphsNode paragraphs;

	public ProcedureSectionNode(String sectionName, ParagraphsNode paragraphs) {
		this.name = sectionName;
		this.paragraphs = paragraphs;
	}

	public void register() {
		getContext().registerParagraph(name, this);
		paragraphs.register();
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return paragraphs.executeGeneric(frame);
	}

}
