package de.cobolj.parser;

import de.cobolj.statements.stop.StopStatementNode;

public class StopStatementVisitor extends Cobol85BaseVisitor<StopStatementNode> {

	@Override
	public StopStatementNode visitStopStatement(Cobol85Parser.StopStatementContext ctx) {
		return new StopStatementNode();
	}
}
