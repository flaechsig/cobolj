package de.cobolj.nodes;

import java.util.Collection;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.parser.statement.nextsentence.NextSentenceExcetion;

/**
 * Klammer um Sentence und Pragraph
 * 
 */
@NodeInfo(shortName = "Paragraphs")
public class ParagraphsNode extends StructureNode {
	@Children
	private final SentenceNode[] sentences;
	@Children
	private final ParagraphNode[] paragraphs;

	public ParagraphsNode(List<SentenceNode> sentenceList, List<ParagraphNode> paragraphList) {
		this.sentences = sentenceList.toArray(new SentenceNode[] {});
		this.paragraphs = paragraphList.toArray(new ParagraphNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for (SentenceNode sentence : sentences) {
			try {
				last = sentence.executeGeneric(frame);
			} catch (NextSentenceExcetion e) {
				continue;
			}
		}

		for (ParagraphNode paragraph : paragraphs) {
			try {
				last = paragraph.executeGeneric(frame);
			} catch (NextSentenceExcetion e) {
				continue;
			}
		}
		return last;
	}

	public void register() {
		for( ParagraphNode paragraph : paragraphs) {
			paragraph.register();
		}
	}
}
