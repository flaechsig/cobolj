package de.cobolj.parser.statement.exit;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.ExitStatementContext;
import de.cobolj.statement.exit.ExitStatementNode;

/**
 * exitStatement : EXIT PROGRAM? ;
 * 
 * @author flaechsig
 *
 */
public class ExitStatementVisitor extends Cobol85BaseVisitor<ExitStatementNode> {
	@Override
	public ExitStatementNode visitExitStatement(ExitStatementContext ctx) {
		return new ExitStatementNode();
	}
}
