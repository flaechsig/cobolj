package de.cobolj.parser.division.procedure;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.ParagraphNameContext;

/**
 * 
 * paragraphName: cobolWord | integerLiteral
 * 
 * @author flaechsig
 *
 */
public class ParagraphNameVisitor extends Cobol85BaseVisitor<String> {

	@Override
	public String visitParagraphName(ParagraphNameContext ctx) {
		if(ctx.cobolWord() != null) {
			return ctx.cobolWord().getText();
		} else {
			return ctx.integerLiteral().getText();
		}
	}
}
