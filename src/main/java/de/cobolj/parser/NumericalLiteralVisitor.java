package de.cobolj.parser;

import java.math.BigDecimal;

import de.cobolj.nodes.BigDecimalNode;
import de.cobolj.nodes.LongNode;
import de.cobolj.nodes.NumberNode;

/**
 * 
 * numericLiteral: 
 *  NUMERICLITERAL
 *   | ZERO
 *   | integerLiteral
 *   
 * @author flaechsig
 *
 */
public class NumericalLiteralVisitor extends Cobol85BaseVisitor<NumberNode> {

	@Override
	public NumberNode visitNumericLiteral(Cobol85Parser.NumericLiteralContext ctx) {
		if(ctx.ZERO() != null)  {
			return new LongNode(0);
		} else if(ctx.integerLiteral() != null) {
			return new LongNode(Long.valueOf(ctx.integerLiteral().getText()));
		} else if(ctx.NUMERICLITERAL() != null) {
			return new BigDecimalNode(new BigDecimal(ctx.NUMERICLITERAL().getText()));
		}
		throw new RuntimeException("Unerwarteter Typ");
	}
}
