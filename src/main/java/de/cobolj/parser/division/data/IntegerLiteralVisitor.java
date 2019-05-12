package de.cobolj.parser.division.data;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.ObjectUtils;

import de.cobolj.division.data.IntegerNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.IntegerLiteralContext;

/**
 * integerLiteral : INTEGERLITERAL | LEVEL_NUMBER_66 | LEVEL_NUMBER_77 |
 * LEVEL_NUMBER_88 ;
 * 
 * @author flaechsig
 *
 */
public class IntegerLiteralVisitor extends Cobol85BaseVisitor<IntegerNode> {
	@Override
	public IntegerNode visitIntegerLiteral(IntegerLiteralContext ctx) {

		TerminalNode terminal = ObjectUtils.firstNonNull(ctx.INTEGERLITERAL(), ctx.LEVEL_NUMBER_66(),
				ctx.LEVEL_NUMBER_77(), ctx.LEVEL_NUMBER_88());

		Integer number = new Integer(terminal.getText());

		return new IntegerNode(number);
	}
}
