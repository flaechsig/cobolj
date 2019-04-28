package de.cobolj.parser.statement.unstring;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.UnstringDelimitPhraseContext;
import de.cobolj.parser.statement.LiteralOrIdentifierVisitor;
import de.cobolj.statement.unstring.UnstringDelimitNode;

/**
 * unstringDelimitPhrase : DELIMITED BY? ALL? literalOrIdentifier ;
 * 
 * @author flaechsig
 *
 */
public class UnstringDelimitPhraseVisitor extends Cobol85BaseVisitor<UnstringDelimitNode> {
	@Override
	public UnstringDelimitNode visitUnstringDelimitPhrase(UnstringDelimitPhraseContext ctx) {
		boolean all = (ctx.ALL()!=null)?true:false;
		ExpressionNode identifier = ctx.literalOrIdentifier().accept(new LiteralOrIdentifierVisitor());
		return new UnstringDelimitNode(identifier, all);
	}

}
