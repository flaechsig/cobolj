package de.cobolj.parser.statement.call;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import static de.cobolj.parser.ParserHelper.*;
import de.cobolj.parser.statement.LiteralOrIdentifierVisitor;
import de.cobolj.parser.Cobol85Parser.CallStatementContext;
import de.cobolj.statement.call.CallStatementNode;
import de.cobolj.statement.call.CallUsingPhraseNode;

/**
 * callStatement : CALL ( progName=literalOrIdentifier ) callUsingPhrase?
 * onOverflowPhrase? onExceptionClause? notOnExceptionClause?
 * END_CALL? ;
 * 
 * @author flaechsig
 *
 */
public class CallStatementVisitor extends Cobol85BaseVisitor<CallStatementNode> {
	@Override
	public CallStatementNode visitCallStatement(CallStatementContext ctx) {
		notImplemented(ctx.onOverflowPhrase());
		notImplemented(ctx.onExceptionClause());
		notImplemented(ctx.notOnExceptionClause());
		
		ExpressionNode progName = accept(ctx.progName, new LiteralOrIdentifierVisitor());
		CallUsingPhraseNode callUsingPhrase = accept(ctx.callUsingPhrase(), new CallUsingPhraseVisitor());
		
		return new CallStatementNode(progName, callUsingPhrase);
	}
}
