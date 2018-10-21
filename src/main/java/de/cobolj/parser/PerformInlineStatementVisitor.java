package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.PerformTypeNode;
import de.cobolj.odes.PerformStatementNode;
import de.cobolj.parser.Cobol85Parser.PerformInlineStatementContext;
import de.cobolj.statements.StatementNode;

/**
 * 
 * performInlineStatement: performType? statement* END_PERFORM
 * 
 * @author flaechsig
 *
 */
public class PerformInlineStatementVisitor extends Cobol85BaseVisitor<PerformStatementNode> {
	@Override
	public PerformStatementNode visitPerformInlineStatement(PerformInlineStatementContext ctx) {
		PerformTypeNode node=null;
		List<StatementNode> statements = ctx.statement()
				.stream()
				.map(stmt -> stmt.accept(new StatementVisitor()))
				.collect(Collectors.toList());
		
		if(ctx.performType() != null) {
			node = ctx.performType().accept(new PerformTypeVisitor(statements));
		}
		return new PerformStatementNode(node);
	}
}
