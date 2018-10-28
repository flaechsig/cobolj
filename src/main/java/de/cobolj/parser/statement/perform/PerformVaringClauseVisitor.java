package de.cobolj.parser.statement.perform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformAfterContext;
import de.cobolj.parser.Cobol85Parser.PerformVaryingClauseContext;

/**
 * 
 * performVaryingClause: VARYING performVaryingPhrase performAfter*
 * 
 * @author flaechsig
 *
 */
public class PerformVaringClauseVisitor extends Cobol85BaseVisitor<ExpressionNode> {

	private final ExpressionNode perform;
	private final boolean testBefore;

	public PerformVaringClauseVisitor(boolean testBefore, ExpressionNode perform) {
		this.perform = perform;
		this.testBefore = testBefore;
	}

	@Override
	public ExpressionNode visitPerformVaryingClause(PerformVaryingClauseContext ctx) {
		ExpressionNode performVarying = perform;

		List<PerformAfterContext> performAfter = new ArrayList<>(ctx.performAfter());
		Collections.reverse(performAfter); 
		for(PerformAfterContext childCtx : performAfter) {
			performVarying = childCtx.accept(new PerformAfterVisitor(testBefore, performVarying));
		}

		return ctx.performVaryingPhrase().accept(new PerformVaryingPhraseVisitor(testBefore, performVarying));
	}

}
