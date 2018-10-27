package de.cobolj.parser.statement.perform;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformVaryingContext;
import de.cobolj.statements.perform.PerformStatementNode;
import de.cobolj.statements.perform.PerformTypeNode;

/**
 * 
 * performVarying: performTestClause performVaryingClause | performVaryingClause
 * performTestClause?
 * 
 * @author flaechsig
 *
 */
public class PerformVaryingVisitor extends Cobol85BaseVisitor<PerformTypeNode> {
	private PerformStatementNode perform;

	public PerformVaryingVisitor(PerformStatementNode perform) {
		this.perform = perform;
	}

	@Override
	public PerformTypeNode visitPerformVarying(PerformVaryingContext ctx) {
		Boolean testBefore = true;
		if (ctx.performTestClause() != null) {
			testBefore = ctx.performTestClause().accept(new PerformTestClauseVisitor());
		}
		return ctx.performVaryingClause().accept(new PerformVaringClauseVisitor(testBefore, perform));
	}
}
