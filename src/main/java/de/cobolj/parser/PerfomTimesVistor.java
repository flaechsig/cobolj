package de.cobolj.parser;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85Parser.PerformTimesContext;
import de.cobolj.util.ExpressionNodeFactory;

/**
 * 
 * performTimes: (identifier | integerLiteral) TIMES
 * 
 * @author flaechsig
 *
 */
public class PerfomTimesVistor extends Cobol85BaseVisitor<ExpressionNode> {
	@Override
	public ExpressionNode visitPerformTimes(PerformTimesContext ctx) {
		return ExpressionNodeFactory.create(ctx.integerLiteral(), ctx.identifier());
	}
}
