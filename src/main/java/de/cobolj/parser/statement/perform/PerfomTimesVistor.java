package de.cobolj.parser.statement.perform;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.PerformTimesContext;
import de.cobolj.statements.perform.PerformStatementNode;
import de.cobolj.statements.perform.PerformTimesNode;
import de.cobolj.statements.perform.PerformTypeNode;
import de.cobolj.util.ExpressionNodeFactory;

/**
 * 
 * performTimes: (identifier | integerLiteral) TIMES
 * 
 * @author flaechsig
 *
 */
public class PerfomTimesVistor extends Cobol85BaseVisitor<PerformTypeNode> {
	
	private PerformStatementNode perform;

	public PerfomTimesVistor(PerformStatementNode perform) {
		this.perform = perform;
	}

	@Override
	public PerformTypeNode visitPerformTimes(PerformTimesContext ctx) {
		ExpressionNode condition = ExpressionNodeFactory.create(ctx.integerLiteral(), ctx.identifier());
		return new PerformTimesNode(condition, perform);
	}
}
