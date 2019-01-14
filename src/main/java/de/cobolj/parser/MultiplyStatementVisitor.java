package de.cobolj.parser;

import de.cobolj.parser.Cobol85Parser.MultiplyStatementContext;
import de.cobolj.parser.statement.add.MathImplNode;
import de.cobolj.phrase.SizePhraseNode;
import de.cobolj.statement.MathStatementNode;

/**
 * multiplyStatement:
 * 	(multiplyByStatement | multiplyGivingStatement)
 *  onSizeErrorPhrase? notOnSizeErrorPhrase? END_MULTIPLY?
 *  
 * @author flaechsig
 *
 */
public class MultiplyStatementVisitor extends Cobol85BaseVisitor<MathStatementNode> {
	
@Override
public MathStatementNode visitMultiplyStatement(MultiplyStatementContext ctx) {
	MathImplNode math = null; 
	SizePhraseNode errorPhrase = null;
	SizePhraseNode successPhrase = null;
	if(ctx.onSizeErrorPhrase() != null) {
		errorPhrase = ctx.onSizeErrorPhrase().accept(new SizePhraseVisitor());
	}
	if(ctx.notOnSizeErrorPhrase() != null ) {
		successPhrase = ctx.notOnSizeErrorPhrase().accept(new SizePhraseVisitor());
	}
	if(ctx.multiplyByStatement() != null) {
		math = ctx.multiplyByStatement().accept(new MultiplyByStatementVisitor());
	} else {
		math = ctx.multiplyGivingStatement().accept(new MultiplyGivingStatementVisitor());
	}

	math.setSizeErrorCheck(errorPhrase != null || successPhrase != null);
	return new MathStatementNode(math, errorPhrase, successPhrase);
}
}
