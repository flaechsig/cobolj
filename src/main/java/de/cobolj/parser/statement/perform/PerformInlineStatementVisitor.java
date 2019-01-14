package de.cobolj.parser.statement.perform;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformInlineStatementContext;
import de.cobolj.statement.StatementNode;
import de.cobolj.statement.perform.PerformInlineStatementNode;
import de.cobolj.statement.perform.PerformOneTimeNode;
import de.cobolj.statement.perform.PerformTypeNode;
import de.cobolj.parser.StatementVisitor;

/**
 * 
 * performInlineStatement: performType? statement* END_PERFORM
 * 
 * @author flaechsig
 *
 */
public class PerformInlineStatementVisitor extends Cobol85BaseVisitor<PerformTypeNode> {
	@Override
	public PerformTypeNode visitPerformInlineStatement(PerformInlineStatementContext ctx) {
		PerformTypeNode node=null;
		List<StatementNode> statements = ctx.statement()
				.stream()
				.map(stmt -> stmt.accept(new StatementVisitor()))
				.collect(Collectors.toList());
		ExpressionNode perform = new PerformInlineStatementNode(statements);
		if(ctx.performType() != null) {
			node = ctx.performType().accept(new PerformTypeVisitor(perform));
		} else {
			node = new PerformOneTimeNode(perform);
		}
		return node;
	}
}
