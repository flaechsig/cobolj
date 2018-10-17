package de.cobolj.parser;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.util.ExpressionNodeFactory;

/**
 * 
 * moveToSendingArea: identifier | literal;
 * 
 * @author flaechsig
 *
 */
public class MoveToSendingAreaVisitor extends Cobol85BaseVisitor<ExpressionNode> {

	@Override
	public ExpressionNode visitMoveToSendingArea(Cobol85Parser.MoveToSendingAreaContext ctx) {
		return ExpressionNodeFactory.create(ctx.literal(), ctx.identifier());
	}
}
