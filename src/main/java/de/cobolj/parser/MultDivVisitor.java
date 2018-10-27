package de.cobolj.parser;

import de.cobolj.nodes.BigDecimalNode;
import de.cobolj.nodes.DivNode;
import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.MultNode;
import de.cobolj.parser.Cobol85Parser.MultDivContext;

/**
 * 
 * multDiv: (ASTERISKCHAR | SLASHCHAR) powers
 * 
 * @author flaechsig
 *
 */
public class MultDivVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	/** Argument, auf das diese Node angewendet wird */
	private ExpressionNode leftPowers;

	public MultDivVisitor(ExpressionNode powers) {
		this.leftPowers = powers;
	}

	@Override
	public ExpressionNode visitMultDiv(MultDivContext ctx) {
		BigDecimalNode rightPowers = (BigDecimalNode) ctx.powers().accept(new PowersVisitor());
		if(ctx.ASTERISKCHAR() != null) {
			return new MultNode(leftPowers, rightPowers);
		} else {
			return new DivNode(leftPowers, rightPowers);
		}
	}
}
