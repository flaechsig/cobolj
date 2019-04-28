package de.cobolj.parser.statement.stop;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.ParserHelper;
import de.cobolj.statement.stop.StopStatementNode;

/**
 * stopStatement : STOP ( RUN | literal ) ;
 * 
 * @author flaechsig
 *
 */
public class StopStatementVisitor extends Cobol85BaseVisitor<StopStatementNode> {

	@Override
	public StopStatementNode visitStopStatement(Cobol85Parser.StopStatementContext ctx) {
		ParserHelper.notImplemented(ctx.literal());
		
		return new StopStatementNode();
	}
}
