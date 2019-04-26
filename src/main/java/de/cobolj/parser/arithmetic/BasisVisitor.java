package de.cobolj.parser.arithmetic;

import de.cobolj.nodes.ArithmeticNode;
import de.cobolj.nodes.BasisNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.BasisContext;
import de.cobolj.util.ArithmeticNodeFactory;

/**
 * 
 * basis: LPARENCHAR arithmeticExpression RPARENCHAR | identifier | literal
 * 
 * @author flaechsig
 *
 */
public class BasisVisitor extends Cobol85BaseVisitor<ArithmeticNode> {
	@Override
	public ArithmeticNode visitBasis(BasisContext ctx) {
		if (ctx.arithmeticExpression() != null) {
			return ctx.arithmeticExpression().accept(new ArithmeticExpressionVisitor());
		} else {
			return new BasisNode(ArithmeticNodeFactory.create(ctx.literal(), ctx.identifier()));
		}
	}
}
