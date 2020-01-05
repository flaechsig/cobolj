package de.cobolj.nodes;

import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.CobolCallableNode;
import de.cobolj.parser.StartRuleVisitor;


public class ProcedureSectionNode extends StructureNode {
	private final RootCallTarget callTarget;

	public ProcedureSectionNode(String sectionName, ParagraphsNode paragraphs) {
		List<CobolNode> para = new ArrayList<CobolNode>();
		para.add(paragraphs);

		new CobolCallableNode(StartRuleVisitor.language, sectionName, para); 
		this.callTarget = CobolCallableNode.findByName(sectionName);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return callTarget.call();
	}

}
