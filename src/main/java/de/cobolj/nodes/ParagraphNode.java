package de.cobolj.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import de.cobolj.parser.statement.nextsentence.NextSentenceExcetion;
import de.cobolj.statement.gotostmt.GotoException;

/**
 * Diese Klasse bildet einen einzelnen Paragraphen eines Cobol-Programms ab.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "Paragraph")
public class ParagraphNode extends StructureNode {
	private final String name;
	@Children
	private final SentenceNode[] sentences;
	
	public ParagraphNode(String name, List<SentenceNode> sentences ) {
	    this.name = name;
		this.sentences = sentences.toArray(new SentenceNode[] {});
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) throws GotoException{
		Object last = null;
		for (SentenceNode s : sentences) {
			try {
				last = s.executeGeneric(frame);
			} catch (NextSentenceExcetion e) {
				continue;
			}
		}
		return last;
	}

    public void register() {
	    getContext().registerParagraph(name, this);
    }
}
