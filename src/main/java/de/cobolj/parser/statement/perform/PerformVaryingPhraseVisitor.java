package de.cobolj.parser.statement.perform;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformVaryingPhraseContext;
import de.cobolj.parser.IdentifierVisitor;

/**
 * 
 * performVaryingPhrase: (identifier | literal) performFrom performBy performUntil
 * 
 * @author flaechsig
 *
 */
public class PerformVaryingPhraseVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	
	private final ExpressionNode perform;
	private final boolean testBefore;

	public PerformVaryingPhraseVisitor(boolean testBefore, ExpressionNode perform) {
		this.perform = perform; 
		this.testBefore = testBefore;
	}

	@Override
	public ExpressionNode visitPerformVaryingPhrase(PerformVaryingPhraseContext ctx) {
		String var;
		if(ctx.identifier()!=null) {
			var = ctx.identifier().accept(IdentifierVisitor.INSTANCE);
		} else  /* index */ {
			throw new RuntimeException("Not Implemented");
		} 
		ExpressionNode start = ctx.performFrom().accept(new PerformFromVisitor());
		ExpressionNode step = ctx.performBy().accept(new PerformByVisitor());
		return ctx.performUntil().accept(new PerformVaryingUntilVisitor(perform, testBefore, var, start, step));
	}
}
