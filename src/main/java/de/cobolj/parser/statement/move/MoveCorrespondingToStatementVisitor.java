package de.cobolj.parser.statement.move;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.MoveCorrespondingToStatementContext;

/**
 * 
 * moveCorrespondingToStatement : ( CORRESPONDING | CORR )
 * moveCorrespondingToSendingArea TO identifier+
 * 
 * @author flaechsig
 *
 */
public class MoveCorrespondingToStatement extends Cobol85BaseVisitor {
	@Override
	public Object visitMoveCorrespondingToStatement(MoveCorrespondingToStatementContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMoveCorrespondingToStatement(ctx);
	}
}
