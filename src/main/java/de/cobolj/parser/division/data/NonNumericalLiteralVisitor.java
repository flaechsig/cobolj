package de.cobolj.parser.division.data;

import org.antlr.v4.runtime.tree.TerminalNode;

import de.cobolj.nodes.StringNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Lexer;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.NonNumericLiteralContext;
import de.cobolj.util.HexNumber;
import de.cobolj.util.StringLiteral;

/**
 * 
 * nonNumericLiteral: STRINGLITERAL | DBCSLITERAL | HEXNUMBER | NULLTERMINATED
 * 
 * @author flaechsig
 *
 */
public class NonNumericalLiteralVisitor extends Cobol85BaseVisitor<StringNode> {

	@Override
	public StringNode visitNonNumericLiteral(Cobol85Parser.NonNumericLiteralContext ctx) {
		int lexer = ((TerminalNode)ctx.getChild(0)).getSymbol().getType();
		switch (lexer) {
		case Cobol85Lexer.STRINGLITERAL:
			return new StringNode(new StringLiteral(ctx.STRINGLITERAL().getText()).toString());
		case Cobol85Lexer.HEXNUMBER:
			return new StringNode(new HexNumber(ctx.HEXNUMBER().getText()).toString());
		default:
			throw new RuntimeException("Unbekanntes Statement :" + Cobol85Lexer.ruleNames[lexer-1]);
		}
	}
}
