package de.cobolj.parser;

import de.cobolj.parser.Cobol85Parser.PerformStatementContext;
import de.cobolj.statements.perform.PerformTypeNode;

/**
 * performStatement: PERFORM  (performInlineStatement  | performProcedureStatement  )
 * 
 * @author flaechsig
 *
 */
public class PerformStatementVisitor extends Cobol85BaseVisitor<PerformTypeNode> {

	@Override
	public PerformTypeNode visitPerformStatement(PerformStatementContext ctx) {
		if(ctx.performInlineStatement() != null) {
			return ctx.performInlineStatement().accept(new PerformInlineStatementVisitor());
		} else /* performProcedureStatement */ {
			return ctx.performProcedureStatement().accept(new PerformProcedureStatementVisitor());
		}
	}
}
