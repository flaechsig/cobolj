package de.cobolj.parser;

import de.cobolj.parser.Cobol85Parser.WriteStatementContext;
import de.cobolj.statement.write.WriteStatementNode;

/**
 * WRITE recordName writeFromPhrase? writeAdvancingPhrase?
 * writeAtEndOfPagePhrase? writeNotAtEndOfPagePhrase? invalidKeyPhrase?
 * notInvalidKeyPhrase? END_WRITE?
 * 
 * @author flaechsig
 *
 */
public class WriteStatementVisitor extends Cobol85BaseVisitor<WriteStatementNode> {

	@Override
	public WriteStatementNode visitWriteStatement(WriteStatementContext ctx) {
		ParserHelper.notImplemented(ctx.writeFromPhrase());
		ParserHelper.notImplemented(ctx.writeAdvancingPhrase());
		ParserHelper.notImplemented(ctx.writeAtEndOfPagePhrase());
		ParserHelper.notImplemented(ctx.writeNotAtEndOfPagePhrase());
		ParserHelper.notImplemented(ctx.invalidKeyPhrase());
		ParserHelper.notImplemented(ctx.notInvalidKeyPhrase());
		
		String recordName = ctx.recordName.accept(QualifiedDataNameVisitor.INSTANCE).toString();
		return new WriteStatementNode(recordName);
	}
}
