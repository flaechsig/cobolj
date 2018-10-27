package de.cobolj.parser.condition;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.ConditionContext;

/**
 * 
 * condition: combinableCondition andOrCondition*
 * 
 * @author flaechsig
 *
 */
public class ConditionVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	
	@Override
	public ExpressionNode visitCondition(ConditionContext ctx) {
		ExpressionNode result = ctx.combinableCondition().accept(new CombinableConditionVisitor());
		if(ctx.andOrCondition().size() >0 ) {
			throw new RuntimeException("Not implemented");
		}
		return result;
	}

}
