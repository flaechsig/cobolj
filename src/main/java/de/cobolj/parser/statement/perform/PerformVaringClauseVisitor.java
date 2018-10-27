package de.cobolj.parser.statement.perform;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformVaryingClauseContext;
import de.cobolj.statements.perform.PerformStatementNode;
import de.cobolj.statements.perform.PerformTypeNode;

/**
 * 
 * performVaryingClause: VARYING performVaryingPhrase performAfter*
 * 
 * @author flaechsig
 *
 */
public class PerformVaringClauseVisitor extends Cobol85BaseVisitor<PerformTypeNode> {
	
	private final PerformStatementNode perform;
	private final boolean testBefore;

	public PerformVaringClauseVisitor(boolean testBefore, PerformStatementNode perform) {
		this.perform = perform;
		this.testBefore = testBefore;
	}

	@Override
	public PerformTypeNode visitPerformVaryingClause(PerformVaryingClauseContext ctx) {
		PerformTypeNode node = ctx.performVaryingPhrase().accept(new PerformVaryingPhraseVisitor(testBefore, perform));
		if(ctx.performAfter().size()>0) {
			throw new RuntimeException("Not implemented");
//			ctx.performAfter().add(new PerformAfterVisitor());
		}
		return node;
	}
}
