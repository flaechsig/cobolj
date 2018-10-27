package de.cobolj.parser;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PerformStatementNode;
import de.cobolj.nodes.PerformTypeNode;
import de.cobolj.nodes.PerformUntilNode;
import de.cobolj.parser.Cobol85Parser.PerformUntilContext;

/**
 * 
 * performUntil: performTestClause? UNTIL condition
 * 
 * @author flaechsig
 *
 */
public class PerformUntilVisitor extends Cobol85BaseVisitor<PerformTypeNode> {
	
	private PerformStatementNode perform;

	public PerformUntilVisitor(PerformStatementNode perform) {
		this.perform = perform;
	}

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
