package de.cobolj.parser;

import de.cobolj.parser.Cobol85Parser.DivideStatementContext;
import de.cobolj.parser.statement.add.MathImplNode;
import de.cobolj.phrase.SizePhraseNode;
import de.cobolj.statements.MathStatementNode;

/**
 * 
 * @author flaechsig
 *
 */
public class DivideStatementVisitor extends Cobol85BaseVisitor<MathStatementNode> {

	@Override
	public MathStatementNode visitDivideStatement(DivideStatementContext ctx) {
		MathImplNode math = null; 
		SizePhraseNode errorPhrase = null;
		SizePhraseNode successPhrase = null;
		if(ctx.onSizeErrorPhrase() != null) {
			errorPhrase = ctx.onSizeErrorPhrase().accept(new SizePhraseVisitor());
		}
		if(ctx.notOnSizeErrorPhrase() != null ) {
			successPhrase = ctx.notOnSizeErrorPhrase().accept(new SizePhraseVisitor());
		}
		
		if(ctx.divideIntoStatement() != null) {
			math = ctx.divideIntoStatement().accept(new DivideIntoStatementVisitor());
		} else if(ctx.divideIntoGivingStatement() != null) {
			math = ctx.divideIntoGivingStatement().accept(new DivideIntoGivingStatementVisitor());
		} else if(ctx.divideByGivingStatement() != null) {
			math = ctx.divideByGivingStatement().accept(new DivideByGivingStatementVisitor());
		} else if(ctx.divideIntoGivingStatementRemainder() != null) {
			math = ctx.divideIntoGivingStatementRemainder().accept(new DivideIntoGivingRemainderStatementVisitor());
		} else if(ctx.divideByGivingStatementRemainder() != null) {
			math = ctx.divideByGivingStatementRemainder().accept(new DivideByGivingRemainderStatementVisitor());
		}

		math.setSizeErrorCheck(errorPhrase != null || successPhrase != null);
		return new MathStatementNode(math, errorPhrase, successPhrase);
	}
}
