package de.cobolj.parser.statement.ifstmt;

import de.cobolj.nodes.BlockNode;
import de.cobolj.nodes.ConditionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.IfStatementContext;
import de.cobolj.parser.condition.ConditionVisitor;

/**
 * ifStatement: IF condition ifThen ifElse? END_IF?
 * 
 * @author flaechsig
 *
 */
public class IfStatementVisitor extends Cobol85BaseVisitor<IfStatementNode> {
	@Override
	public IfStatementNode visitIfStatement(IfStatementContext ctx) {
		ConditionNode condition = ctx.condition().accept(new ConditionVisitor());
		BlockNode ifThen = ctx.ifThen().accept(new IfThenVisitor());
		BlockNode ifElse = ctx.ifElse().accept(new IfElseVisitor());
		return new IfStatementNode(condition, ifThen, ifElse);
	}
}
