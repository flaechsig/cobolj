package de.cobolj.parser;

import de.cobolj.odes.PerformStatementNode;
import de.cobolj.parser.Cobol85Parser.PerformStatementContext;

/**
 * performStatement: PERFORM  (performInlineStatement  | performProcedureStatement  )
 * 
 * @author flaechsig
 *
 */
public class PerformStatementVisitor extends Cobol85BaseVisitor<PerformStatementNode> {

	@Override
	public PerformStatementNode visitPerformStatement(PerformStatementContext ctx) {
		if(ctx.performInlineStatement() != null) {
			return ctx.performInlineStatement().accept(new PerformInlineStatementVisitor());
		} else {
			throw new RuntimeException("Not Implemented");
		}
	}
}
