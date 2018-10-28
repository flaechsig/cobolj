package de.cobolj.parser.condition;

import de.cobolj.nodes.ConditionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.SimpleConditionContext;

/**
 * 
 * simpleCondition: LPARENCHAR condition RPARENCHAR | relationCondition |
 * classCondition | conditionNameReference
 * 
 * @author flaechsig
 *
 */
public class SimpleConditionVisitor extends Cobol85BaseVisitor<ConditionNode> {

	@Override
	public ConditionNode visitSimpleCondition(SimpleConditionContext ctx) {
		if (ctx.condition() != null) {
			throw new RuntimeException("Not implemented");
		} else if (ctx.relationCondition() != null) {
			return ctx.relationCondition().accept(new RelationConditionVisitor());
		} else if (ctx.classCondition() != null) {
			throw new RuntimeException("Not implemented");
		} else /* conditionalNameReference */ {
			throw new RuntimeException("Not implemented");
		}
	}
}
