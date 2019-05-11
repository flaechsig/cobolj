package de.cobolj.parser.statement.write;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.ParserHelper;
import de.cobolj.parser.Cobol85Parser.WriteStatementContext;
import de.cobolj.parser.CobolWordVisitor;
import de.cobolj.parser.division.data.QualifiedDataNameVisitor;
import de.cobolj.statement.write.WriteStatementNode;

/**
 * writeStatement : WRITE recordName = cobolWord writeFromPhrase?
 * writeAdvancingPhrase? writeAtEndOfPagePhrase? writeNotAtEndOfPagePhrase?
 * invalidKeyPhrase? notInvalidKeyPhrase? END_WRITE? ;
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

		String recordName = ctx.recordName.accept(CobolWordVisitor.INSTANCE);
		return new WriteStatementNode(recordName);
	}
}
