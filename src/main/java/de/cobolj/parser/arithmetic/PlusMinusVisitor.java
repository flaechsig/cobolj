package de.cobolj.parser.arithmetic;

import de.cobolj.nodes.AddNode;
import de.cobolj.nodes.ArithmeticNode;
import de.cobolj.nodes.SubstractNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.PlusMinusContext;

/**
 * 
 * plusMinus: (PLUSCHAR | MINUSCHAR) multDivs
 * 
 * @author flaechsig
 *
 */
public class PlusMinusVisitor extends Cobol85BaseVisitor<ArithmeticNode> {
	private ArithmeticNode left;
	
	public PlusMinusVisitor(ArithmeticNode left) {
		this.left = left;
	}

	@Override
	public ArithmeticNode visitPlusMinus(PlusMinusContext ctx) {
		ArithmeticNode right = ctx.multDivs().accept(new MultDivsVisitor());
		if(ctx.PLUSCHAR() != null) {
			return new AddNode(left, right);
		} else {
			return new SubstractNode(left, right);
		}
	}
}
