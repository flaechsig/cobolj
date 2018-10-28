package de.cobolj.parser;

import de.cobolj.nodes.ArithmeticNode;
import de.cobolj.parser.Cobol85Parser.MultDivContext;
import de.cobolj.parser.Cobol85Parser.MultDivsContext;

/**
 * 
 * multDivs: powers multDiv*
 * 
 * @author flaechsig
 *
 */
public class MultDivsVisitor extends Cobol85BaseVisitor<ArithmeticNode> {
	@Override
	public ArithmeticNode visitMultDivs(MultDivsContext ctx) {
		ArithmeticNode powers = ctx.powers().accept(new PowersVisitor());
		for(MultDivContext childCtx: ctx.multDiv()) {
			powers = childCtx.accept(new MultDivVisitor(powers));
		}
		return powers;
	}
}
