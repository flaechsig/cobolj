package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.MoveToStatementNode;

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
		List<FrameSlot> receiving;

		sending = ctx.moveToSendingArea().accept(new MoveToSendingAreaVisitor());

		receiving = ctx.identifier().stream().map(id -> id.accept(IdentifierVisitor.INSTANCE))
				.collect(Collectors.toList());

		return new MoveToStatementNode(sending, receiving);
	}

}
