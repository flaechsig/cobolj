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
	@Children
	private final SentenceNode[] sentences;
	
	public ParagraphNode(String name, List<SentenceNode> sentences ) {
		PARAGRAPH_REGISTRY.put(name.toUpperCase(), this);
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
}
