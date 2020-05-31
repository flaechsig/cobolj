package de.cobolj.parser.division.procedure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import de.cobolj.nodes.ParagraphNode;
import de.cobolj.nodes.ParagraphsNode;
import de.cobolj.nodes.SentenceNode;
import de.cobolj.nodes.StructureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;

/**
 * Langsam wird es konkret. Diese Klasse bündelt Sentences und Paragraph
 * 
 * paragraphs: sentence* paragraph*
 * 
 * @author flaechsig
 *
 */
public class ParagraphsVisitor extends Cobol85BaseVisitor<ParagraphsNode> {

	@Override
	public ParagraphsNode visitParagraphs(Cobol85Parser.ParagraphsContext ctx) {
		List<ParagraphNode> paragraphList = new ArrayList<>();
		SentenceVisitor visitor = new SentenceVisitor();

		// Pseudo-Paragraph für die ersten Sentences
		List<SentenceNode> sentenceList = ctx.sentence()
			.stream()
			.map(sentence -> sentence.accept(visitor))
			.collect(Collectors.toList());
		if(!sentenceList.isEmpty()) {
			paragraphList.add(new ParagraphNode("TMP_"+ new Random().nextInt(), sentenceList));
		}

		paragraphList.addAll(ctx.paragraph()
					.stream()
					.map(para -> para.accept(new ParagraphVisitor()))
					.collect(Collectors.toList()));
		return new ParagraphsNode(paragraphList);
	}
}
