package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.ParagraphsNode;

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
		List<CobolNode> paragraphOrSentence = ctx.sentence()
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
