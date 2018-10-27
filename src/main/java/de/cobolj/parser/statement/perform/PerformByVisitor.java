package de.cobolj.parser.statement.perform;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.ArithmeticExpressionVisitor;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformByContext;
import de.cobolj.util.ExpressionNodeFactory;

/**
 * 
 * performBy: BY (identifier | literal | arithmeticExpression)
 * 
 * @author flaechsig
 *
 */
public class PerformByVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	@Override
	public ExpressionNode visitPerformBy(PerformByContext ctx) {
		if(ctx.arithmeticExpression()!=null) {
			return ctx.arithmeticExpression().accept(new ArithmeticExpressionVisitor());
		} else {
			return ExpressionNodeFactory.create(ctx.literal(), ctx.identifier());
		}
	}
}
