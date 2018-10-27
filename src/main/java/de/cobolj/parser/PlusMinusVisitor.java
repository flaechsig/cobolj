package de.cobolj.parser;

import de.cobolj.nodes.AddNode;
import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.SubstractNode;
import de.cobolj.parser.Cobol85Parser.PlusMinusContext;

/**
 * 
 * plusMinus: (PLUSCHAR | MINUSCHAR) multDivs
 * 
 * @author flaechsig
 *
 */
public class PlusMinusVisitor extends Cobol85BaseVisitor<ExpressionNode> {
	private ExpressionNode left;
	
	public PlusMinusVisitor(ExpressionNode left) {
		this.left = left;
	}

	@Override
	public ExpressionNode visitPlusMinus(PlusMinusContext ctx) {
		ExpressionNode right = ctx.multDivs().accept(new PowersVisitor());
		if(ctx.PLUSCHAR() != null) {
			return new AddNode(left, right);
		} else {
			return new SubstractNode(left, right);
		}
	}
}
