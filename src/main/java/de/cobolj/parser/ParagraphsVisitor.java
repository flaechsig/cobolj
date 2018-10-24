package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.ParagraphsNode;
import de.cobolj.nodes.StructureNode;

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
		SentenceVisitor visitor = new SentenceVisitor();
		List<StructureNode> paragraphOrSentence = ctx.sentence()
			.stream()
			.map(sentence -> sentence.accept(visitor))
			.collect(Collectors.toList());
		paragraphOrSentence.addAll(
				ctx.paragraph()
					.stream()
					.map(para -> para.accept(new ParagraphVisitor()))
					.collect(Collectors.toList())
				);
		return new ParagraphsNode(paragraphOrSentence);
	}
}
