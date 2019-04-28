package de.cobolj.parser.statement.unstring;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.UnstringStatementContext;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.ParserHelper;
import de.cobolj.parser.PhraseVisitor;
import de.cobolj.parser.QualifiedDataNameVisitor;
import de.cobolj.phrase.PhraseNode;
import de.cobolj.statement.unstring.UnstringDelimitNode;
import de.cobolj.statement.unstring.UnstringInto;
import de.cobolj.statement.unstring.UnstringStatementNode;

/**
 * unstringStatement : UNSTRING sending=identifier (unstringDelimitPhrase
 * unstringOrAllPhrase*)? INTO unstringInto+ (WITH? POINTER
 * pointer=qualifiedDataName)? (TALLYING IN? tallying=qualifiedDataName)?
 * onOverflowPhrase? notOnOverflowPhrase? END_UNSTRING? ;
 * 
 * @author flaechsig
 *
 */
public class UnstringStatementVisitor extends Cobol85BaseVisitor<UnstringStatementNode> {

	@Override
	public UnstringStatementNode visitUnstringStatement(UnstringStatementContext ctx) {
		PictureNode sending = new PictureNode(ctx.sending.accept(IdentifierVisitor.INSTANCE));

		List<UnstringDelimitNode> delimiters = new ArrayList<>();
		if (ctx.unstringDelimitPhrase() != null) {
			delimiters.add(ctx.unstringDelimitPhrase().accept(new UnstringDelimitPhraseVisitor()));
		}
		delimiters.addAll(ctx.unstringOrAllPhrase().stream()
				.map(result -> result.accept(new UnstringOrAllPhraseVisitor())).collect(Collectors.toList()));

		List<UnstringInto> unstringInto = ctx.unstringInto().stream()
				.map(result -> result.accept(new UnstringIntoVisitor())).collect(Collectors.toList());

		PictureNode tallying = null;
		if (ctx.tallying != null) {
			tallying = new PictureNode(ctx.tallying.accept(QualifiedDataNameVisitor.INSTANCE));
		}
		
		PictureNode pointer = null;
		if(ctx.pointer != null) {
			pointer = new PictureNode(ctx.pointer.accept(QualifiedDataNameVisitor.INSTANCE));
		}
		
		PhraseNode onOverflow = null;
		if(ctx.onOverflowPhrase() != null) {
			onOverflow = ctx.onOverflowPhrase().accept(new PhraseVisitor());
		}
		PhraseNode notOverflow = null;
		if(ctx.onOverflowPhrase() != null) {
			notOverflow = ctx.notOnOverflowPhrase().accept(new PhraseVisitor());
		}

		return new UnstringStatementNode(sending, delimiters, unstringInto, tallying, pointer, onOverflow, notOverflow);
	}

}
