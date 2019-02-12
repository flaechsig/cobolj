package de.cobolj.parser.statement.add;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.PhraseVisitor;
import de.cobolj.phrase.PhraseNode;
import de.cobolj.statement.MathStatementNode;

/**
 * 
 * addStatement: ADD ( addToStatement | addToGivingStatement |
 * addCorrespondingStatement ) onSizeErrorPhrase? notOnSizeErrorPhrase? END_ADD?
 *
 * @author flaechsig
 *
 */
public class AddStatementVisitor extends Cobol85BaseVisitor<MathStatementNode> {

	@Override
	public MathStatementNode visitAddStatement(Cobol85Parser.AddStatementContext ctx) {
		MathImplNode add = null; // mandatory
		PhraseNode errorPhrase = null; // optional
		PhraseNode successPhrase = null; // optional

		if (ctx.addToStatement() != null) {
			add = ctx.addToStatement().accept(new AddToStatementVisitor());
		} else if (ctx.addToGivingStatement() != null) {
			add = ctx.addToGivingStatement().accept(new AddToGivingStatementVisitor());
		} else if (ctx.addCorrespondingStatement() != null) {
			throw new RuntimeException("Nicht implementiert");
		}

		if (ctx.onSizeErrorPhrase() != null) {
			errorPhrase = ctx.onSizeErrorPhrase().accept(new PhraseVisitor());
		}

		if (ctx.notOnSizeErrorPhrase() != null) {
			successPhrase = ctx.notOnSizeErrorPhrase().accept(new PhraseVisitor());
		}
		add.setSizeErrorCheck(errorPhrase != null || successPhrase != null);

		return new MathStatementNode(add, errorPhrase, successPhrase);
	}
}
