package de.cobolj.parser.statement.perform;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformVaryingPhraseContext;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.statements.perform.PerformStatementNode;
import de.cobolj.statements.perform.PerformTypeNode;

/**
 * 
 * performVaryingPhrase: (identifier | literal) performFrom performBy performUntil
 * 
 * @author flaechsig
 *
 */
public class PerformVaryingPhraseVisitor extends Cobol85BaseVisitor<PerformTypeNode> {
	
	private final PerformStatementNode perform;
	private final boolean testBefore;

	public PerformVaryingPhraseVisitor(boolean testBefore, PerformStatementNode perform) {
		this.perform = perform; 
		this.testBefore = testBefore;
	}

	@Override
	public PerformTypeNode visitPerformVaryingPhrase(PerformVaryingPhraseContext ctx) {
		FrameSlot var;
		if(ctx.identifier()!=null) {
			var = ctx.identifier().accept(IdentifierVisitor.INSTANCE);
		} else  {
			throw new RuntimeException("Not Implemented");
		} 
		ExpressionNode start = ctx.performFrom().accept(new PerformFromVisitor());
		ExpressionNode step = ctx.performBy().accept(new PerformByVisitor());
		return ctx.performUntil().accept(new PerformVaryingUntilVisitor(perform, testBefore, var, start, step));
	}
}
