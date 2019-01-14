package de.cobolj.parser.statement.perform;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformVaryingContext;
import de.cobolj.statement.perform.PerformTypeNode;

/**
 * 
 * performVarying: performTestClause performVaryingClause | performVaryingClause
 * performTestClause?
 * 
 * @author flaechsig
 *
 */
public class PerformVaryingVisitor extends Cobol85BaseVisitor<PerformTypeNode> {
	private ExpressionNode perform;

	public PerformVaryingVisitor(ExpressionNode perform) {
		this.perform = perform;
	}

	@Override
	public PerformTypeNode visitPerformVarying(PerformVaryingContext ctx) {
		Boolean testBefore = true;
		if (ctx.performTestClause() != null) {
			testBefore = ctx.performTestClause().accept(new PerformTestClauseVisitor());
		}
		ExpressionNode childExpression = ctx.performVaryingClause().accept(new PerformVaringClauseVisitor(testBefore, perform));
		return new PerformVaryingNode(childExpression);
	}
}
