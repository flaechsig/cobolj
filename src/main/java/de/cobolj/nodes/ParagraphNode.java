package de.cobolj.nodes;

import java.util.List;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.CobolCallableNode;
import de.cobolj.CobolLanguage;

/**
 * Diese Klasse bildet einen einzelnen Paragraphen eines Cobol-Programms ab.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "Paragraph")
public class ParagraphNode extends StructureNode {
	private final RootCallTarget callTarget;
	public ParagraphNode(CobolLanguage language, String name, List<SentenceNode> sentences) {
		this.callTarget = CobolCallableNode.findByName(name);
	}
	
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return callTarget.call();
	}
}
