package de.cobolj.parser.statement.add;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.util.ExpressionNodeFactory;

public class AddToGivenVisitor extends Cobol85BaseVisitor<ExpressionNode> {

	@Override
	public ExpressionNode visitAddToGiving(Cobol85Parser.AddToGivingContext ctx) {
		return ExpressionNodeFactory.create(ctx.literal(), ctx.identifier());
	}
}
