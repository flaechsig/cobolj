package de.cobolj.nodes;

import java.util.Collection;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.parser.statement.nextsentence.NextSentenceExcetion;

/**
 * Klammer um Sentence und Pragraph
 * 
 */
@NodeInfo(shortName = "Paragraphs")
public class ParagraphsNode extends CobolNode {
	@Children
	private final CobolNode[] paragraphOrSentence;

	public ParagraphsNode(Collection<? extends StructureNode> paragraphOrSentence) {
		this.paragraphOrSentence = paragraphOrSentence.toArray(new CobolNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for (CobolNode childNode : paragraphOrSentence) {
			try {
				last = childNode.executeGeneric(frame);
			} catch (NextSentenceExcetion e) {
				continue;
			}
		}
		return last;
	}

}
