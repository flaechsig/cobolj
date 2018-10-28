package de.cobolj.parser;

import de.cobolj.nodes.ArithmeticNode;
import de.cobolj.nodes.DivNode;
import de.cobolj.nodes.MultNode;
import de.cobolj.parser.Cobol85Parser.MultDivContext;

/**
 * 
 * multDiv: (ASTERISKCHAR | SLASHCHAR) powers
 * 
 * @author flaechsig
 *
 */
public class MultDivVisitor extends Cobol85BaseVisitor<ArithmeticNode> {
	/** Argument, auf das diese Node angewendet wird */
	private ArithmeticNode leftPowers;

	public MultDivVisitor(ArithmeticNode powers) {
		this.leftPowers = powers;
	}

	@Override
	public ArithmeticNode visitMultDiv(MultDivContext ctx) {
		ArithmeticNode rightPowers = ctx.powers().accept(new PowersVisitor());
		if(ctx.ASTERISKCHAR() != null) {
			return new MultNode(leftPowers, rightPowers);
		} else {
			return new DivNode(leftPowers, rightPowers);
		}
	}
}
