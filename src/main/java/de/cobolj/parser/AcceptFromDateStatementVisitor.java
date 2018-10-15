package de.cobolj.parser;

import org.antlr.v4.runtime.tree.TerminalNode;

import de.cobolj.statements.accept.DateTimeInputNode;
import de.cobolj.statements.accept.InputNode;
import de.cobolj.statements.accept.DateTimeInputNode.DateTimePattern;

/**
 * acceptFromDateStatement: FROM ( DATE YYYYMMDD? | DAY YYYYDDD? | DAY_OF_WEEK |
 * TIME | TIMER | TODAYS_DATE MMDDYYYY? | TODAYS_NAME | YEAR | YYYYMMDD |
 * YYYYDDD )
 * 
 * @author flaechsig
 *
 */
public class AcceptFromDateStatementVisitor extends Cobol85BaseVisitor<InputNode> {

	public InputNode visitAcceptFromDateStatement(Cobol85Parser.AcceptFromDateStatementContext ctx) {
		DateTimePattern pattern = null;
		InputNode result = null;

		TerminalNode term = (TerminalNode) ctx.getChild(1);
		switch (term.getSymbol().getType()) {
		case Cobol85Lexer.DATE:
			if (ctx.YYYYMMDD() == null) {
				pattern = DateTimePattern.DATE_YY;
				break;
			}
			// ggf. weiter laufen
		case Cobol85Lexer.YYYYMMDD:
			pattern = DateTimePattern.DATE_YYYY;
			break;
		case Cobol85Lexer.DAY:
			if (ctx.YYYYDDD() == null) {
				pattern = DateTimePattern.DAY_YY;
				break;
			}
			// ggf. weiter laufen
		case Cobol85Lexer.YYYYDDD:
			pattern = DateTimePattern.DAY_YYYY;
			break;
		case Cobol85Lexer.TODAYS_DATE:
			if (ctx.MMDDYYYY() == null) {
				pattern = DateTimePattern.TODAYS_DATE_YY;
				break;
			}
			// ggf. weiter laufen
		case Cobol85Lexer.MMDDYYYY:
			pattern = DateTimePattern.TODAYS_DATE_YYYY;
			break;
		case Cobol85Lexer.DAY_OF_WEEK:
			pattern = DateTimePattern.DAY_OF_WEEK;
			break;
		case Cobol85Lexer.TIME:
			pattern = DateTimePattern.TIME;
			break;
		case Cobol85Lexer.TIMER:
			pattern = DateTimePattern.TIMER;
			break;
		case Cobol85Lexer.YEAR:
			pattern = DateTimePattern.YEAR;
			break;
		case Cobol85Lexer.TODAYS_NAME:
			pattern = DateTimePattern.TODAYS_NAME;
			break;
		default:
			throw new RuntimeException("Unerwartete Kontellation");
		}

		return new DateTimeInputNode(pattern);
	}
}
