package de.cobolj.nodes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Diese Klasse bildet einen einzelnen Paragraphen eines Cobol-Programms ab.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "Paragraph")
public class ParagraphNode extends CobolNode {
	
	/** Sortiertes Verzeichnis aller Paragraphen. Sortiert nach der Einfügereihenfolge */
	private static Map<String, ParagraphNode> allParagraphs = new LinkedHashMap<>();
	
	/** Alle Sätze des Paragraphen */
	@Children
	private final SentenceNode[] sentences;
	
	public ParagraphNode(String name, List<SentenceNode> sentences) {
		allParagraphs.put(name.toUpperCase(), this);
		this.sentences = sentences.toArray(new SentenceNode[] {});
	}
	
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		for(SentenceNode sentence : sentences) {
			sentence.executeGeneric(frame);
		}
		return null;
	}
}
