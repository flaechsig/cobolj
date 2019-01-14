package de.cobolj.parser.statement.continuestmt;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.ContinueStatementContext;
import de.cobolj.statement.continuestmt.ContinueStatementNode;

/**
 * continueStatement:
 *     CONTINUE
 *     
 * Aus Gründen der Vereinheitlichung wird dieses Statement ausimplementiert. Es ist jedoch eine No-Operation
 * 
 * @author flaechsig
 *
 */
public class ContinueStatementVisitor extends Cobol85BaseVisitor<ContinueStatementNode> {
@Override
public ContinueStatementNode visitContinueStatement(ContinueStatementContext ctx) {
	return new ContinueStatementNode();
}
}
