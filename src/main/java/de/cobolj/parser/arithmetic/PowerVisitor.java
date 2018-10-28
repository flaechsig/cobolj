package de.cobolj.parser.arithmetic;

import de.cobolj.nodes.ArithmeticNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.PowerContext;

/**
 * 
 * power: DOUBLEASTERISKCHAR basis
 * 
 * @author flaechsig
 *
 */
public class PowerVisitor extends Cobol85BaseVisitor<ArithmeticNode> {
	@Override
	public ArithmeticNode visitPower(PowerContext ctx) {
		return ctx.basis().accept(new BasisVisitor());
	}
}
