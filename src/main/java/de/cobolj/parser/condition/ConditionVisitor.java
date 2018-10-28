package de.cobolj.parser.condition;

import de.cobolj.nodes.ConditionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.ConditionContext;

/**
 * 
 * condition: combinableCondition andOrCondition*
 * 
 * @author flaechsig
 *
 */
public class ConditionVisitor extends Cobol85BaseVisitor<ConditionNode> {
	
	@Override
	public ConditionNode visitCondition(ConditionContext ctx) {
		ConditionNode result = ctx.combinableCondition().accept(new CombinableConditionVisitor());
		if(ctx.andOrCondition().size() >0 ) {
			throw new RuntimeException("Not implemented");
		}
		return result;
	}

}
