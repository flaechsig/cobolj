package de.cobolj.parser.statement.ifstmt;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.BlockNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.IfElseContext;
import de.cobolj.parser.StatementVisitor;
import de.cobolj.parser.statement.nextsentence.NextSentenceNode;
import de.cobolj.statement.StatementNode;

/**
 * 
 * ifElse: ELSE (NEXT SENTENCE | statement*)
 * 
 * @author flaechsig
 *
 */
public class IfElseVisitor extends Cobol85BaseVisitor<BlockNode> {
	@Override
	public BlockNode visitIfElse(IfElseContext ctx) {
		List<StatementNode> statements = ctx.statement().stream().map(stmt -> stmt.accept(new StatementVisitor()))
				.collect(Collectors.toList());
		if (ctx.SENTENCE() != null) {
			statements.add(new NextSentenceNode());
		}
		return new BlockNode(statements);
	}

}
