package de.cobolj.parser;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85Parser.ReadIntoContext;
import de.cobolj.util.ExpressionNodeFactory;

/**
 * readInto : INTO identifier ;
 * 
 * @author flaechsig
 *
 */
public class ReadIntoVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	@Override
	public ExpressionNode visitReadInto(ReadIntoContext ctx) {
		return ExpressionNodeFactory.create(ctx.identifier());
	}
}
