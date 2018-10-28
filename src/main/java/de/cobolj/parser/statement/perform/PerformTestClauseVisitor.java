package de.cobolj.parser.statement.perform;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformTestClauseContext;

/**
 * 
 * performTestClause: WITH? TEST (BEFORE | AFTER)
 * 
 * @author flaechsig
 *
 */
public class PerformTestClauseVisitor extends Cobol85BaseVisitor<Boolean> {

	@Override
	public Boolean visitPerformTestClause(PerformTestClauseContext ctx) {
		return ctx.BEFORE() != null;
	}
}
