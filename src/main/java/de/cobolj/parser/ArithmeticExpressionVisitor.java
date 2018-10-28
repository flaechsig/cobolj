package de.cobolj.parser;

import de.cobolj.nodes.ArithmeticNode;
import de.cobolj.parser.Cobol85Parser.ArithmeticExpressionContext;
import de.cobolj.parser.Cobol85Parser.PlusMinusContext;

/**
 * 
 * arithmeticExpression: multDivs plusMinus*
 * 
 * @author flaechsig
 *
 */
public class ArithmeticExpressionVisitor extends Cobol85BaseVisitor<ArithmeticNode> {
	@Override
	public ArithmeticNode visitArithmeticExpression(ArithmeticExpressionContext ctx) {
		ArithmeticNode result = ctx.multDivs().accept(new MultDivsVisitor());
		for(PlusMinusContext childCtx : ctx.plusMinus()) {
			result = childCtx.accept(new PlusMinusVisitor(result));
		}
		return result;
	}
}
