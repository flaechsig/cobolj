package de.cobolj.parser;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85Parser.ReadStatementContext;
import de.cobolj.phrase.PhraseNode;
import de.cobolj.statement.read.ReadStatementNode;

/**
 * readStatement : READ fileName=IDENTIFIER NEXT? RECORD? readInto? readWith?
 * readKey? invalidKeyPhrase? notInvalidKeyPhrase? atEndPhrase? notAtEndPhrase?
 * END_READ? ;
 * 
 * @author flaechsig
 *
 */
public class ReadStatementVisitor extends Cobol85BaseVisitor<ReadStatementNode> {
	@Override
	public ReadStatementNode visitReadStatement(ReadStatementContext ctx) {
		ParserHelper.notImplemented(ctx.NEXT());
		ParserHelper.notImplemented(ctx.RECORD());
		ParserHelper.notImplemented(ctx.readWith());
		ParserHelper.notImplemented(ctx.readKey());
		ParserHelper.notImplemented(ctx.invalidKeyPhrase());
		ParserHelper.notImplemented(ctx.notInvalidKeyPhrase());

		String file = ctx.fileName.getText();
		PhraseNode atEnd = null;
		PhraseNode notAtEnd = null;
		ExpressionNode readInto = null;
		if (ctx.atEndPhrase() != null) {
			atEnd = ctx.atEndPhrase().accept(new PhraseVisitor());
		}
		if (ctx.notAtEndPhrase() != null) {
			notAtEnd = ctx.notAtEndPhrase().accept(new PhraseVisitor());
		}
		if(ctx.readInto() != null) {
			readInto = ctx.readInto().accept(new ReadIntoVisitor());
		}

		return new ReadStatementNode(file, readInto, atEnd, notAtEnd);
	}
}
