package de.cobolj.parser.arithmetic;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.ArithmeticNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.PowersNode;
import de.cobolj.parser.Cobol85Parser.PowersContext;

/**
 * 
 * powers: (PLUSCHAR | MINUSCHAR)? basis power*
 *
 * @author flaechsig
 *
 */
public class PowersVisitor extends Cobol85BaseVisitor<ArithmeticNode> {
	@Override
	public ArithmeticNode visitPowers(PowersContext ctx) {
		ArithmeticNode basis = ctx.basis().accept(new BasisVisitor());
		List<ArithmeticNode> powers;
		PowerVisitor visitor = new PowerVisitor();
		powers = ctx.power()
				.stream()
				.map(pow -> pow.accept(visitor))
				.collect(Collectors.toList());
		return new PowersNode(basis, powers);
	}
}
