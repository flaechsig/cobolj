package de.cobolj.parser.statement.perform;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformVaryingPhraseContext;
import de.cobolj.parser.IdentifierVisitor;

/**
 * 
 * performVaryingPhrase: (identifier | indexName) performFrom performBy performUntil
 * 
 * @author flaechsig
 *
 */
public class PerformAfterVaryingVisitor extends Cobol85BaseVisitor<ExpressionNode> {

	private final ExpressionNode perform;
	private final boolean testBefore;

	public PerformAfterVaryingVisitor(boolean testBefore, ExpressionNode perform) {
		this.perform = perform;
		this.testBefore = testBefore;
	}

	@Override
	public ExpressionNode visitPerformVaryingPhrase(PerformVaryingPhraseContext ctx) {
		FrameSlot var;
		if (ctx.identifier() != null) {
			var = ctx.identifier().accept(IdentifierVisitor.INSTANCE);
		} else {
			throw new RuntimeException("Not Implemented");
		}
		ExpressionNode start = ctx.performFrom().accept(new PerformFromVisitor());
		ExpressionNode step = ctx.performBy().accept(new PerformByVisitor());
		return ctx.performUntil().accept(new PerformVaryingUntilAfterVisitor(perform, testBefore, var, start, step));
	}
}
