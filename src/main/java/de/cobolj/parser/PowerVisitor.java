package de.cobolj.parser;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85Parser.PowerContext;

/**
 * 
 * power: DOUBLEASTERISKCHAR basis
 * 
 * @author flaechsig
 *
 */
public class PowerVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	@Override
	public ExpressionNode visitPower(PowerContext ctx) {
		return ctx.basis().accept(new BasisVisitor());
	}
}
