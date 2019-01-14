package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85Parser.OnSizeErrorPhraseContext;
import de.cobolj.phrase.SizePhraseNode;
import de.cobolj.statement.StatementNode;

/**
 * Erzeugt die Node für ON SIZE ERROR und NOT ON SIZE ERROR
 * 
 * @author flaechsig
 *
 */
public class SizePhraseVisitor extends Cobol85BaseVisitor<SizePhraseNode> {

	@Override
	public SizePhraseNode visitNotOnSizeErrorPhrase(Cobol85Parser.NotOnSizeErrorPhraseContext ctx) {
		return new SizePhraseNode(createStatementList(ctx.statement()));
	}

	public SizePhraseNode visitOnSizeErrorPhrase(OnSizeErrorPhraseContext ctx) {
		return new SizePhraseNode(createStatementList(ctx.statement()));
	}

	private List<StatementNode> createStatementList(List<Cobol85Parser.StatementContext> statements) {
		List<StatementNode> result;
		result = statements.stream().map(stmt -> stmt.accept(new StatementVisitor())).collect(Collectors.toList());
		return result;
	}
}
