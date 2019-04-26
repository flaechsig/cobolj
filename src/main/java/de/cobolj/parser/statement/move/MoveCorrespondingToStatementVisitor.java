package de.cobolj.parser.statement.move;

import java.util.List;
import java.util.stream.Collectors;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.MoveCorrespondignToStatementNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.MoveCorrespondingToStatementContext;
import de.cobolj.parser.IdentifierVisitor;

/**
 * 
 * moveCorrespondingToStatement : ( CORRESPONDING | CORR )
 * moveCorrespondingToSendingArea=identifier TO moveCorrespondingToReceivingArea+=identifier+
 * 
 * @author flaechsig
 *
 */
public class MoveCorrespondingToStatementVisitor extends Cobol85BaseVisitor<MoveCorrespondignToStatementNode> {
	@Override
	public MoveCorrespondignToStatementNode visitMoveCorrespondingToStatement(MoveCorrespondingToStatementContext ctx) {
		String sending = ctx.moveCorrespondingToSendingArea.accept(IdentifierVisitor.INSTANCE);
		List<String> receiving = ctx.moveCorrespondingToReceivingArea
				.stream()
				.map(result -> result.accept(IdentifierVisitor.INSTANCE))
				.collect(Collectors.toList());
		
		return new MoveCorrespondignToStatementNode(sending, receiving);
	}
}
