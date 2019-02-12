package de.cobolj.parser.statement.subtract;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.PhraseVisitor;
import de.cobolj.parser.Cobol85Parser.SubtractStatementContext;
import de.cobolj.parser.statement.add.MathImplNode;
import de.cobolj.phrase.PhraseNode;
import de.cobolj.statement.MathStatementNode;

/**
 * subtractStatement: SUBTRACT ( subtractFromStatement |
 * subtractFromGivingStatement | subtractCorrespondingStatement )
 * onSizeErrorPhrase? notOnSizeErrorPhrase? END_SUBTRACT?
 * 
 * @author flaechsig
 *
 */
public class SubtractStatementVisitor extends Cobol85BaseVisitor<MathStatementNode> {
	
	@Override
	public MathStatementNode visitSubtractStatement(SubtractStatementContext ctx) {
		MathImplNode sub = null; 
		PhraseNode errorPhrase = null;
		PhraseNode successPhrase = null;
		if(ctx.onSizeErrorPhrase() != null) {
			errorPhrase = ctx.onSizeErrorPhrase().accept(new PhraseVisitor());
		}
		if(ctx.notOnSizeErrorPhrase() != null ) {
			successPhrase = ctx.notOnSizeErrorPhrase().accept(new PhraseVisitor());
		}
		if(ctx.subtractFromStatement() != null) {
			sub = ctx.subtractFromStatement().accept(new SubtractFromStatementVisitor());
		} else if(ctx.subtractFromGivingStatement() != null) {
			sub = ctx.subtractFromGivingStatement().accept(new SubtractFromGivingStatementVisitor());
		} else {
			throw new RuntimeException("Not Implemented");
		}
		sub.setSizeErrorCheck(errorPhrase != null || successPhrase != null);
		
		return new MathStatementNode(sub, errorPhrase, successPhrase);
	}
}
