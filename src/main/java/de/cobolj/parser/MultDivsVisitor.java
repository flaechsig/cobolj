package de.cobolj.parser;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85Parser.MultDivContext;
import de.cobolj.parser.Cobol85Parser.MultDivsContext;

/**
 * 
 * multDivs: powers multDiv*
 * 
 * @author flaechsig
 *
 */
public class MultDivsVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	@Override
	public ExpressionNode visitMultDivs(MultDivsContext ctx) {
		ExpressionNode powers = ctx.powers().accept(new PowersVisitor());
		for(MultDivContext childCtx: ctx.multDiv()) {
			powers = childCtx.accept(new MultDivVisitor(powers));
		}
		return powers;
	}
}
