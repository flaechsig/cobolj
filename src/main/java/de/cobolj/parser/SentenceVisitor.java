package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.SentenceNode;
import de.cobolj.parser.statement.StatementVisitor;
import de.cobolj.statement.StatementNode;

/**
 * 
 * sentence: statement* DOT_FS;
 * 
 * @author flaechsig
 *
 */
public class SentenceVisitor extends Cobol85BaseVisitor<SentenceNode> {

	@Override
	public SentenceNode visitSentence(Cobol85Parser.SentenceContext ctx) {
		StatementVisitor visitor = new StatementVisitor();
		List<StatementNode> statements = ctx.statement()
				.stream()
				.map(statement -> statement.accept(visitor))
				.collect(Collectors.toList());
		return new SentenceNode(statements);
	}
}
