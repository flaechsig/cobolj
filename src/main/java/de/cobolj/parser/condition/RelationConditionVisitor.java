package de.cobolj.parser.condition;

import de.cobolj.nodes.ConditionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.RelationConditionContext;
import de.cobolj.parser.arithmetic.RelationSignConditionVisitor;

/**
 * 
 * relationCondition: relationSignCondition | relationArithmeticComparison |
 * relationCombinedComparison
 * 
 * @author flaechsig
 *
 */
public class RelationConditionVisitor extends Cobol85BaseVisitor<ConditionNode> {

	@Override
	public ConditionNode visitRelationCondition(RelationConditionContext ctx) {
		if (ctx.relationSignCondition() != null) {
			return ctx.relationSignCondition().accept(new RelationSignConditionVisitor());
		} else if (ctx.relationArithmeticComparison() != null) {
			return ctx.relationArithmeticComparison().accept(new RelationArithmeticCoparisonVisitor());
		} else /* relationCombinedComparison */ {
			throw new RuntimeException("Not Implemented");
		}
	}
}
