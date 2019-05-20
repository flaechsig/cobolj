package de.cobolj.parser.statement.move;

import static de.cobolj.parser.ParserHelper.accept;

import java.util.List;

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
		ExpressionNode sending = accept(ctx.moveToSendingArea(), new MoveToSendingAreaVisitor());
		List<PictureNode> receiving = accept(ctx.identifier(), IdentifierVisitor.INSTANCE);
		
		return new MoveToStatementNode(sending, receiving);
	}

}
