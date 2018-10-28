package de.cobolj.parser.statement.perform;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformAfterContext;

/**
 * 
 * performAfter: AFTER performVaryingPhrase
 * 
 * @author flaechsig
 *
 */
public class PerformAfterVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	private boolean testBefore;
	private ExpressionNode perform;

	public PerformAfterVisitor(boolean testBefore, ExpressionNode performVarying) {
		this.testBefore = testBefore;
		this.perform = performVarying;
	}

	@Override
	public ExpressionNode visitPerformAfter(PerformAfterContext ctx) {
			return ctx.performVaryingPhrase().accept(new PerformAfterVaryingVisitor(testBefore, perform));
	}
}
