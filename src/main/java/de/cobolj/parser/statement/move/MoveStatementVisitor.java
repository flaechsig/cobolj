package de.cobolj.parser.statement.move;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.statement.StatementNode;
import de.cobolj.statement.move.MoveStatementNode;

/**
 * 
 * moveStatement: MOVE ALL? ( moveToStatement | moveCorrespondingToStatement )
 * 
 * @author flaechsig
 *
 */
public class MoveStatementVisitor extends Cobol85BaseVisitor<MoveStatementNode> {

	@Override
	public MoveStatementNode visitMoveStatement(Cobol85Parser.MoveStatementContext ctx) {
		StatementNode stmt = null;
		if(ctx.moveToStatement() != null) {
			stmt = ctx.moveToStatement().accept(new MoveToStatementVisitor());
		} else  {
			stmt = ctx.moveCorrespondingToStatement().accept(new MoveCorrespondingToStatementVisitor());
		}
		return new MoveStatementNode(stmt);
	}
}
