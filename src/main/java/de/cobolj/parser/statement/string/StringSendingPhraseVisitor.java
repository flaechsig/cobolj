package de.cobolj.parser.statement.string;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.parse.ANTLRParser.element_return;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.StringSendingPhraseContext;
import de.cobolj.parser.statement.LiteralOrIdentifierVisitor;
import de.cobolj.statement.string.StringSendingPhraseNode;
import de.cobolj.statement.string.StringSendingPhraseNode.DelimiterType;

/**
 * stringSendingPhrase : (stringSending=literalOrIdentifier)+ DELIMITED BY? (
 * SIZE | referenz=literalOrIdentifier ) ;
 * 
 * @author flaechsig
 *
 */
public class StringSendingPhraseVisitor extends Cobol85BaseVisitor<StringSendingPhraseNode> {
	@Override
	public StringSendingPhraseNode visitStringSendingPhrase(StringSendingPhraseContext ctx) {
		List<ExpressionNode> stringSending = ctx.stringSending.stream()
				.map(result -> result.accept(new LiteralOrIdentifierVisitor()))
				.collect(Collectors.toList());
		DelimiterType delimiter = null;
		ExpressionNode referenz = null;
		if(ctx.SIZE() != null )  {
			delimiter = DelimiterType.SIZE;
		} else if (ctx.referenz.identifier() != null) {
			delimiter = DelimiterType.IDENTIFIER;
		} else {
			delimiter = DelimiterType.LITERAL;
		}
		if(!delimiter.equals(DelimiterType.SIZE)) {
			referenz = ctx.referenz.accept(new LiteralOrIdentifierVisitor());
		}
		return new StringSendingPhraseNode(stringSending, delimiter, referenz);
	}
}
