package de.cobolj.parser;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.RelationSignConditionNode;
import de.cobolj.parser.Cobol85Parser.RelationSignConditionContext;

/**
 * 
 * relationSignCondition: arithmeticExpression IS? NOT? ( POSITIVE | NEGATIVE |
 * ZERO )
 * 
 * @author flaechsig
 *
 */
public class RelationSignConditionVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	@Override
	public ExpressionNode visitRelationSignCondition(RelationSignConditionContext ctx) {
		boolean checkPositive = true;
		int checkCompare = 0;
		ExpressionNode arithmetic;
		
		arithmetic = ctx.arithmeticExpression().accept(new ArithmeticExpressionVisitor());
		if(ctx.NOT() != null) {
			checkPositive = false;
		}
		if(ctx.NEGATIVE() != null) {
			checkCompare = -1;
		} else if(ctx.POSITIVE()!=null) {
			checkCompare = 1;
		}
		
		return new RelationSignConditionNode(arithmetic, checkPositive, checkCompare);
	}
}
