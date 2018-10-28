package de.cobolj.statements.add;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.util.ExpressionNodeFactory;

/**
 * addFrom: identifier | literal
 * 
 * @author flaechsig
 *
 */
public class AddFromVisitor extends Cobol85BaseVisitor<ExpressionNode> {

	@Override 
	public ExpressionNode visitAddFrom(Cobol85Parser.AddFromContext ctx) {
		return ExpressionNodeFactory.create(ctx.literal(), ctx.identifier());
	}
}
