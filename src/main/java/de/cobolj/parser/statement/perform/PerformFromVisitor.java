package de.cobolj.parser.statement.perform;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformFromContext;
import de.cobolj.util.ExpressionNodeFactory;

/**
 * 
 * performFrom: FROM (identifier | indexName)
 * 
 * @author flaechsig
 *
 */
public class PerformFromVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	@Override
	public ExpressionNode visitPerformFrom(PerformFromContext ctx) {
		if (ctx.indexName() != null) {
			throw new RuntimeException("Not implemented");
		} else {
			return ExpressionNodeFactory.create(ctx.literal(), ctx.identifier());
		}
	}
}
