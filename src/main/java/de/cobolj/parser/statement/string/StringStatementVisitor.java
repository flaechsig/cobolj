package de.cobolj.parser.statement.string;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.StringStatementContext;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.ParserHelper;
import de.cobolj.statement.string.StringSendingPhraseNode;
import de.cobolj.statement.string.StringStatementNode;

/**
 * stringStatement : STRING stringSendingPhrase+ INTO
 * stringIntoPhrase=identifier stringWithPointerPhrase? onOverflowPhrase?
 * notOnOverflowPhrase? END_STRING?
 * 
 * @author flaechsig
 *
 */
public class StringStatementVisitor extends Cobol85BaseVisitor<StringStatementNode> {

	@Override
	public StringStatementNode visitStringStatement(StringStatementContext ctx) {
		ParserHelper.notImplemented(ctx.stringWithPointerPhrase());
		ParserHelper.notImplemented(ctx.onOverflowPhrase());
		ParserHelper.notImplemented(ctx.notOnOverflowPhrase());

		List<StringSendingPhraseNode> stringSendingPhrase = ctx.stringSendingPhrase().stream()
				.map(result -> result.accept(new StringSendingPhraseVisitor())).collect(Collectors.toList());
		String stringIntoPhrase = ctx.stringIntoPhrase.accept(IdentifierVisitor.INSTANCE);
		return new StringStatementNode(stringSendingPhrase, stringIntoPhrase);
	}
}
