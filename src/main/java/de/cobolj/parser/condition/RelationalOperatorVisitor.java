package de.cobolj.parser.condition;

import static de.cobolj.parser.ParserUtil.check;

import de.cobolj.nodes.ArithmeticNode;
import de.cobolj.nodes.ConditionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.RelationalOperatorContext;
import de.cobolj.parser.condition.RelationalOperatorNode.Operator;

/**
 * relationalOperator : ( IS | ARE )? ( NOT? ( GREATER THAN? | MORETHANCHAR |
 * LESS THAN? | LESSTHANCHAR | EQUAL TO? | EQUALCHAR ) | NOTEQUALCHAR | GREATER
 * THAN? OR EQUAL TO? | MORETHANOREQUAL | LESS THAN? OR EQUAL TO? |
 * LESSTHANOREQUAL )
 * 
 * @author flaechsig
 *
 */
public class RelationalOperatorVisitor extends Cobol85BaseVisitor<ConditionNode> {
	private ArithmeticNode left;
	private ArithmeticNode right;

	public RelationalOperatorVisitor(ArithmeticNode left, ArithmeticNode right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public ConditionNode visitRelationalOperator(RelationalOperatorContext ctx) {
		RelationalOperatorNode.Operator operator;
		boolean negate = check(ctx.NOT());
		if(check(ctx.GREATER()) || check(ctx.MORETHANCHAR())) {
			operator = Operator.GREATER;
		} else if(check(ctx.LESS())||check(ctx.LESSTHANCHAR())) {
			operator = Operator.LESS;
		} else if(check(ctx.EQUAL()) || check(ctx.EQUALCHAR())) {
			operator = Operator.EQUAL;
		} else if(check(ctx.NOTEQUALCHAR())) {
			negate = !negate;
			operator = Operator.EQUAL;
		} else if(check(ctx.MORETHANOREQUAL()) || (check(ctx.GREATER()) && check(ctx.EQUAL()))) {
			operator = Operator.GREATEREQUAL;
		} else /* less than or equal */ {
			operator = Operator.LESSEQUAL;
		}

		return new RelationalOperatorNode(left, right, operator, negate);
	}
}
