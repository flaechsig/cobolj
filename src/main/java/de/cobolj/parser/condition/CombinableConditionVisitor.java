package de.cobolj.parser.condition;

import de.cobolj.nodes.CombinableCondition;
import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.CombinableConditionContext;

/**
 * 
 * combinableCondition: NOT? simpleCondition
 * 
 * @author flaechsig
 *
 */
public class CombinableConditionVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	@Override
	public ExpressionNode visitCombinableCondition(CombinableConditionContext ctx) {
		ExpressionNode child = ctx.simpleCondition().accept(new SimpleConditionVisitor());
		return new CombinableCondition(ctx.NOT()==null, child);
	}
}
