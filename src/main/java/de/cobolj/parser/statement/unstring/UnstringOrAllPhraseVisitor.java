package de.cobolj.parser.statement.unstring;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.UnstringOrAllPhraseContext;
import de.cobolj.parser.statement.LiteralOrIdentifierVisitor;
import de.cobolj.statement.unstring.UnstringDelimitNode;

/**
 * 
 * unstringOrAllPhrase : OR ALL? literalOrIdentifier ;
 * 
 * @author flaechsig
 *
 */
public class UnstringOrAllPhraseVisitor extends Cobol85BaseVisitor<UnstringDelimitNode> {

	@Override
	public UnstringDelimitNode visitUnstringOrAllPhrase(UnstringOrAllPhraseContext ctx) {
		boolean all = (ctx.ALL()!=null)?true:false;
		ExpressionNode identifier = ctx.literalOrIdentifier().accept(new LiteralOrIdentifierVisitor());
		return new UnstringDelimitNode(identifier, all);
	}

}
