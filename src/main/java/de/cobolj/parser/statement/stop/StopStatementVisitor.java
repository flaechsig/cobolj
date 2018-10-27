package de.cobolj.parser.statement.stop;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.StopStatementContext;
import de.cobolj.statements.stop.StopStatementNode;

public class StopStatementVisitor extends Cobol85BaseVisitor<StopStatementNode> {

	@Override
	public StopStatementNode visitStopStatement(Cobol85Parser.StopStatementContext ctx) {
		return new StopStatementNode();
	}
}