package de.cobolj.parser.statement.string;

import static de.cobolj.parser.ParserHelper.accept;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.StringStatementContext;
import de.cobolj.parser.division.data.QualifiedDataNameVisitor;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.PhraseVisitor;
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
		
		List<StringSendingPhraseNode> stringSendingPhrase = accept(ctx.stringSendingPhrase(), new StringSendingPhraseVisitor());
		PictureNode stringIntoPhrase = accept(ctx.stringIntoPhrase, IdentifierVisitor.INSTANCE);
		PhraseNode onOverflowPhrase = accept(ctx.onOverflowPhrase(), new PhraseVisitor());
		PhraseNode notOnOverflowPhrase = accept(ctx.notOnOverflowPhrase(), new PhraseVisitor());;
		ExpressionNode stringWithPointerPhrase = accept(ctx.stringWithPointerPhrase(), QualifiedDataNameVisitor.INSTANCE);

		return new StringStatementNode(stringSendingPhrase, stringIntoPhrase, stringWithPointerPhrase, onOverflowPhrase, notOnOverflowPhrase);
	}
}
