package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85Parser.AtEndPhraseContext;
import de.cobolj.parser.Cobol85Parser.NotAtEndPhraseContext;
import de.cobolj.parser.Cobol85Parser.NotOnOverflowPhraseContext;
import de.cobolj.parser.Cobol85Parser.OnOverflowPhraseContext;
import de.cobolj.parser.Cobol85Parser.OnSizeErrorPhraseContext;
import de.cobolj.phrase.PhraseNode;
import de.cobolj.statement.StatementNode;

/**
 * 
 * onSizeErrorPhrase : ON? SIZE ERROR statement* ;
 * notOnSizeErrorPhrase : NOT ON? SIZE ERROR statement* ;
 * atEndPhrase : AT? END statement* ; 
 * notAtEndPhrase : NOT AT? END statement* ;
 * 
 * @author flaechsig
 *
 */
public class PhraseVisitor extends Cobol85BaseVisitor<PhraseNode> {

	@Override
	public PhraseNode visitNotOnSizeErrorPhrase(Cobol85Parser.NotOnSizeErrorPhraseContext ctx) {
		return new PhraseNode(createStatementList(ctx.statement()));
	}

	@Override
	public PhraseNode visitOnSizeErrorPhrase(OnSizeErrorPhraseContext ctx) {
		return new PhraseNode(createStatementList(ctx.statement()));
	}
	
	@Override
	public PhraseNode visitAtEndPhrase(AtEndPhraseContext ctx) {
		return new PhraseNode(createStatementList(ctx.statement()));
	}
	
	@Override
	public PhraseNode visitNotAtEndPhrase(NotAtEndPhraseContext ctx) {
		return new PhraseNode(createStatementList(ctx.statement()));
	}
	
	@Override
	public PhraseNode visitOnOverflowPhrase(OnOverflowPhraseContext ctx) {
		return new PhraseNode(createStatementList(ctx.statement()));
	}
	
	@Override
	public PhraseNode visitNotOnOverflowPhrase(NotOnOverflowPhraseContext ctx) {
		return new PhraseNode(createStatementList(ctx.statement()));
	}

	private List<StatementNode> createStatementList(List<Cobol85Parser.StatementContext> statements) {
		List<StatementNode> result;
		result = statements.stream().map(stmt -> stmt.accept(new StatementVisitor())).collect(Collectors.toList());
		return result;
	}
}
