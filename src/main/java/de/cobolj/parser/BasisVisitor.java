package de.cobolj.parser;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85Parser.BasisContext;
import de.cobolj.util.ExpressionNodeFactory;

/**
 * 
 * basis: 
 *   LPARENCHAR arithmeticExpression RPARENCHAR
 *   | identifier
 *   | literal
 * 
 * @author flaechsig
 *
 */
public class BasisVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	@Override
	public ExpressionNode visitBasis(BasisContext ctx) {
		if(ctx.arithmeticExpression() != null) {
			return ctx.arithmeticExpression().accept(new ArithmeticExpressionVisitor());
		} else {
			return ExpressionNodeFactory.create(ctx.literal(), ctx.identifier());
		}
	}
}
