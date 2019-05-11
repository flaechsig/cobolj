package de.cobolj.parser.statement.move;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.MoveToStatementNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.IdentifierVisitor;

/**
 * 
 * moveToStatement: moveToSendingArea TO identifier+
 * 
 * @author flaechsig
 *
 */
public class MoveToStatementVisitor extends Cobol85BaseVisitor<MoveToStatementNode> {

	@Override
	public MoveToStatementNode visitMoveToStatement(Cobol85Parser.MoveToStatementContext ctx) {
		ExpressionNode sending;
		List<PictureNode> receiving;

		sending = ctx.moveToSendingArea().accept(new MoveToSendingAreaVisitor());

		receiving = ctx.identifier().stream().map(id -> id.accept(IdentifierVisitor.INSTANCE))
				.collect(Collectors.toList());

		return new MoveToStatementNode(sending, receiving);
	}

}
