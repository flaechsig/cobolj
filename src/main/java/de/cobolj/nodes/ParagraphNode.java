package de.cobolj.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

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
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for (SentenceNode s : sentences) {
			last = s.executeGeneric(frame);
		}
		return last;
	}

    public void register() {
	    getContext().registerParagraph(name, this);
    }
}
