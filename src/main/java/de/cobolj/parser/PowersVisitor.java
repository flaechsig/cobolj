package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85Parser.PowersContext;

/**
 * 
 * powers: (PLUSCHAR | MINUSCHAR)? basis power*
 *
 * @author flaechsig
 *
 */
public class PowersVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	@Override
	public ExpressionNode visitPowers(PowersContext ctx) {
		ExpressionNode basis = ctx.basis().accept(new BasisVisitor());
		List<ExpressionNode> powers;
		PowerVisitor visitor = new PowerVisitor();
		powers = ctx.power()
				.stream()
				.map(pow -> pow.accept(visitor))
				.collect(Collectors.toList());
		return new PowersNode(basis, powers);
	}
}
