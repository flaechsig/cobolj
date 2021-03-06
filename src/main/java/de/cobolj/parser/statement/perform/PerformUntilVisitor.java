package de.cobolj.parser.statement.perform;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformUntilContext;
import de.cobolj.parser.condition.ConditionVisitor;
import de.cobolj.statement.perform.PerformTypeNode;
import de.cobolj.statement.perform.PerformUntilNode;

/**
 * 
 * performUntil: performTestClause? UNTIL condition
 * 
 * @author flaechsig
 *
 */
public class PerformUntilVisitor extends Cobol85BaseVisitor<PerformTypeNode> {
	/** Auszuführender Perform-Block */
	private ExpressionNode perform;

	public PerformUntilVisitor(ExpressionNode perform) {
		this.perform = perform;	}

	@Override
	public PerformTypeNode visitPerformUntil(PerformUntilContext ctx) {
		boolean testBefore = true;
		if(ctx.performTestClause() != null) {
			testBefore = ctx.performTestClause().accept(new PerformTestClauseVisitor());
		}
		ExpressionNode condition = ctx.condition().accept(new ConditionVisitor());
		return new PerformUntilNode(testBefore, condition, perform);
	}
}
