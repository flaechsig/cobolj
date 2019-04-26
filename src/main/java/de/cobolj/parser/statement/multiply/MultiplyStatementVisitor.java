package de.cobolj.parser.statement.multiply;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.MultiplyStatementContext;
import de.cobolj.parser.PhraseVisitor;
import de.cobolj.parser.statement.add.MathImplNode;
import de.cobolj.phrase.PhraseNode;
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
	PhraseNode errorPhrase = null;
	PhraseNode successPhrase = null;
	if(ctx.onSizeErrorPhrase() != null) {
		errorPhrase = ctx.onSizeErrorPhrase().accept(new PhraseVisitor());
	}
	if(ctx.notOnSizeErrorPhrase() != null ) {
		successPhrase = ctx.notOnSizeErrorPhrase().accept(new PhraseVisitor());
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
