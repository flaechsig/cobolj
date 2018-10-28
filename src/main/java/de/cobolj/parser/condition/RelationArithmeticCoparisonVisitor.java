package de.cobolj.parser.condition;

import de.cobolj.nodes.ArithmeticNode;
import de.cobolj.nodes.ConditionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.RelationArithmeticComparisonContext;
import de.cobolj.parser.arithmetic.ArithmeticExpressionVisitor;

/**
 * 
 * relationArithmeticComparison: arithmeticExpression relationalOperator arithmeticExpression
 *  
 * @author flaechsig
 *
 */
public class RelationArithmeticCoparisonVisitor extends Cobol85BaseVisitor<ConditionNode> {
	@Override
	public ConditionNode visitRelationArithmeticComparison(RelationArithmeticComparisonContext ctx) {
		ArithmeticNode left = ctx.arithmeticExpression(0).accept(new ArithmeticExpressionVisitor());
		ArithmeticNode right =  ctx.arithmeticExpression(1).accept(new ArithmeticExpressionVisitor());
		return ctx.relationalOperator().accept(new RelationalOperatorVisitor(left, right));
	}
}
