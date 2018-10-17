package de.cobolj.parser;
import de.cobolj.statements.StatementNode;
import de.cobolj.statements.move.MoveStatementNode;

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
			// TODO
		} else  {
			throw new RuntimeException("Not implemented");
		}
		return new MoveStatementNode(stmt);
	}
}
