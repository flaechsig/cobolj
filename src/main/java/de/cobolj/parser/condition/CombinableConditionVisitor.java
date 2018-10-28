package de.cobolj.parser.condition;

import de.cobolj.nodes.CombinableCondition;
import de.cobolj.nodes.ConditionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.CombinableConditionContext;

/**
 * 
 * combinableCondition: NOT? simpleCondition
 * 
 * @author flaechsig
 *
 */
public class CombinableConditionVisitor extends Cobol85BaseVisitor<ConditionNode> {
	@Override
	public ConditionNode visitCombinableCondition(CombinableConditionContext ctx) {
		ConditionNode child = ctx.simpleCondition().accept(new SimpleConditionVisitor());
		return new CombinableCondition(ctx.NOT()==null, child);
	}
}
