package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.division.data.IntegerNode;
import de.cobolj.division.data.SubscriptNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.SubscriptContext;
import de.cobolj.parser.ParserHelper;

/**
 * subscript : ALL | INTEGERLITERAL | qualifiedDataName INTEGERLITERAL? |
 * indexName INTEGERLITERAL? | arithmeticExpression ;
 * 
 * @author flaechsig
 *
 */
public class SubscriptVisitor extends Cobol85BaseVisitor<SubscriptNode> {

	@Override
	public SubscriptNode visitSubscript(SubscriptContext ctx) {
		notImplemented(ctx.ALL());
		notImplemented(ctx.qualifiedDataName());
		notImplemented(ctx.indexName());
		notImplemented(ctx.arithmeticExpression());
		
		IntegerNode integerLiteral = ParserHelper.accept(ctx.integerLiteral(), new IntegerLiteralVisitor());
		
		return new SubscriptNode(integerLiteral);
	}

}
