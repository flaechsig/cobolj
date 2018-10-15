package de.cobolj.parser;

import de.cobolj.statements.display.DisplayOperandNode;
import de.cobolj.util.ExpressionNodeFactory;

/**
 * displayOperand: identifier | literal;
 * 
 * @author flaechsig
 *
 */
public class DisplayOperandVisitor extends Cobol85BaseVisitor<DisplayOperandNode> {

	@Override
	public DisplayOperandNode visitDisplayOperand(Cobol85Parser.DisplayOperandContext ctx) {
		return new DisplayOperandNode(ExpressionNodeFactory.create(ctx.literal(), ctx.identifier()));
	}
}
