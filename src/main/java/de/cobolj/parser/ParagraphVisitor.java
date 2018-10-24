package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.ParagraphNode;
import de.cobolj.nodes.SentenceNode;
import de.cobolj.parser.Cobol85Parser.ParagraphContext;

/**
 * 
 * paragraph: paragraphName DOT_FS (alteredGoTo | sentence* )
 * 
 * @author flaechsig
 *
 */
public class ParagraphVisitor extends Cobol85BaseVisitor<ParagraphNode> {

	@Override
	public ParagraphNode visitParagraph(ParagraphContext ctx) {
		if (ctx.alteredGoTo() != null) {
			throw new RuntimeException("altered GO TO not supported yet (or ever)");
		}

		String name = ctx.paragraphName().accept(new ParagraphNameVisitor());
		List<SentenceNode> sentences = ctx.sentence().stream().map(sentence -> sentence.accept(new SentenceVisitor()))
				.collect(Collectors.toList());

		return new ParagraphNode(StartRuleVisitor.language, name, sentences);
	}

}
