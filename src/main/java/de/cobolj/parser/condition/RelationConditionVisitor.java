package de.cobolj.parser.condition;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.RelationSignConditionVisitor;
import de.cobolj.parser.Cobol85Parser.RelationConditionContext;


/**
 * 
 * relationCondition:
 *   relationSignCondition
 *   | relationArithmeticComparison
 *   | relationCombinedComparison
 * 
 * @author flaechsig
 *
 */
public class RelationConditionVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	
	@Override
	public ExpressionNode visitRelationCondition(RelationConditionContext ctx) {
		if(ctx.relationSignCondition() != null) {
			return ctx.relationSignCondition().accept(new RelationSignConditionVisitor());
		} else {
			throw new RuntimeException("Not Implemented");
		}
	}
}