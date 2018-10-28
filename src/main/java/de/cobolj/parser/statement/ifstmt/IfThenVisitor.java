package de.cobolj.parser.statement.ifstmt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.BlockNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.IfThenContext;
import de.cobolj.parser.StatementVisitor;
import de.cobolj.parser.statement.nextsentence.NextSentenceNode;
import de.cobolj.statements.StatementNode;

/**
 * ifThen: THEN? (NEXT SENTENCE | statement*)
 * 
 * @author flaechsig
 *
 */
public class IfThenVisitor extends Cobol85BaseVisitor<BlockNode> {
	@Override
	public BlockNode visitIfThen(IfThenContext ctx) {
		List<StatementNode> statements = new ArrayList<>();
		if(ctx.SENTENCE() != null) {
			statements.add(new NextSentenceNode());
		}
		statements = ctx.statement()
				.stream()
				.map(stmt -> stmt.accept(new StatementVisitor()))
				.collect(Collectors.toList());
		return new BlockNode(statements);
	}

}
