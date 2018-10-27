package de.cobolj.parser.statement.perform;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.StatementVisitor;
import de.cobolj.parser.Cobol85Parser.PerformInlineStatementContext;
import de.cobolj.statements.StatementNode;
import de.cobolj.statements.perform.PerformInlineStatementNode;
import de.cobolj.statements.perform.PerformOneTimeNode;
import de.cobolj.statements.perform.PerformStatementNode;
import de.cobolj.statements.perform.PerformTypeNode;

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
		PerformStatementNode perform = new PerformInlineStatementNode(statements);
		if(ctx.performType() != null) {
			node = ctx.performType().accept(new PerformTypeVisitor(perform));
		} else {
			node = new PerformOneTimeNode(perform);
		}
		return node;
	}
}
