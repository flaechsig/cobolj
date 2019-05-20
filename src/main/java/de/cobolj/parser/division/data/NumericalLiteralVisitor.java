package de.cobolj.parser.division.data;

import java.math.BigDecimal;

import de.cobolj.nodes.BigDecimalNode;
import de.cobolj.nodes.LongNode;
import de.cobolj.nodes.NumberNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.IntegerLiteralContext;

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
		} else /* ctx.NUMERICLITERAL() */ {
			return new BigDecimalNode(new BigDecimal(ctx.NUMERICLITERAL().getText()));
		}
	}
	
	@Override
	public NumberNode visitIntegerLiteral(IntegerLiteralContext ctx) {
		return new LongNode(Long.valueOf(ctx.INTEGERLITERAL().getText()));
	}
}
