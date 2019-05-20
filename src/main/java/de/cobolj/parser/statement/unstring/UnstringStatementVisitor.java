package de.cobolj.parser.statement.unstring;

import static de.cobolj.parser.ParserHelper.accept;

import java.util.ArrayList;
import java.util.List;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.UnstringStatementContext;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.PhraseVisitor;
import de.cobolj.parser.division.data.QualifiedDataNameVisitor;
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
		PictureNode sending = accept(ctx.sending, IdentifierVisitor.INSTANCE);
		List<UnstringDelimitNode> delimiters = new ArrayList<>();
		if (ctx.unstringDelimitPhrase() != null) {
			delimiters.add(accept(ctx.unstringDelimitPhrase(), new UnstringDelimitPhraseVisitor()));
		}
		delimiters.addAll(accept(ctx.unstringOrAllPhrase(), new UnstringOrAllPhraseVisitor()));
		List<UnstringInto> unstringInto = accept(ctx.unstringInto(), new UnstringIntoVisitor());
		PictureNode tallying = accept(ctx.tallying, QualifiedDataNameVisitor.INSTANCE);
		PictureNode pointer = accept(ctx.pointer, QualifiedDataNameVisitor.INSTANCE);
		PhraseNode onOverflow = accept(ctx.onOverflowPhrase(), new PhraseVisitor());
		PhraseNode notOverflow = accept(ctx.notOnOverflowPhrase(), new PhraseVisitor());

		return new UnstringStatementNode(sending, delimiters, unstringInto, tallying, pointer, onOverflow, notOverflow);
	}

}
