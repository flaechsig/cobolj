package de.cobolj.parser.statement.string;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.StringStatementContext;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.ParserHelper;
import de.cobolj.parser.PhraseVisitor;
import de.cobolj.parser.QualifiedDataNameVisitor;
import de.cobolj.phrase.PhraseNode;
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
		PhraseNode onOverflowPhrase = null;
		PhraseNode notOnOverflowPhrase = null;
		PictureNode stringWithPointerPhrase = null;
		
		List<StringSendingPhraseNode> stringSendingPhrase = ctx.stringSendingPhrase().stream()
				.map(result -> result.accept(new StringSendingPhraseVisitor())).collect(Collectors.toList());
		PictureNode stringIntoPhrase = new PictureNode(ctx.stringIntoPhrase.accept(IdentifierVisitor.INSTANCE));

		if (ctx.onOverflowPhrase() != null) {
			onOverflowPhrase = ctx.onOverflowPhrase().accept(new PhraseVisitor());
		}
		if (ctx.notOnOverflowPhrase() != null) {
			notOnOverflowPhrase = ctx.notOnOverflowPhrase().accept(new PhraseVisitor());
		}
		if (ctx.stringWithPointerPhrase() != null) {
			stringWithPointerPhrase = new PictureNode(ctx.stringWithPointerPhrase().accept(QualifiedDataNameVisitor.INSTANCE));
		}

		return new StringStatementNode(stringSendingPhrase, stringIntoPhrase, stringWithPointerPhrase, onOverflowPhrase, notOnOverflowPhrase);
	}
}
