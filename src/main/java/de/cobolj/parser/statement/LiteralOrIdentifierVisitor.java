package de.cobolj.parser.statement;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.LiteralOrIdentifierContext;
import de.cobolj.util.ExpressionNodeFactory;

/**
 * literalOrIdentifier: identifier | literal
 * 
 * @author flaechsig
 *
 */
public class LiteralOrIdentifierVisitor extends Cobol85BaseVisitor<ExpressionNode> {

	@Override
	public ExpressionNode visitLiteralOrIdentifier(LiteralOrIdentifierContext ctx) {
		return ExpressionNodeFactory.create(ctx.literal(), ctx.identifier());
	}
}
