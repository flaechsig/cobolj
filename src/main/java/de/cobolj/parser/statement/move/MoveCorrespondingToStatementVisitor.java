package de.cobolj.parser.statement.move;

import static de.cobolj.parser.ParserHelper.accept;

import java.util.List;

import de.cobolj.nodes.MoveCorrespondignToStatementNode;
import de.cobolj.nodes.PictureNode;
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
		PictureNode sending = accept(ctx.moveCorrespondingToSendingArea, IdentifierVisitor.INSTANCE);
		List<PictureNode> receiving = accept(ctx.moveCorrespondingToReceivingArea, IdentifierVisitor.INSTANCE);
		
		return new MoveCorrespondignToStatementNode(sending, receiving);
	}
}
