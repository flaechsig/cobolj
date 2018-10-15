package de.cobolj.parser;

import de.cobolj.phrase.SizePhraseNode;
import de.cobolj.statements.add.AddStatementNode;

/**
 * 
 * addStatement:
 *   ADD
 *   (
 *       addToStatement
 *       | addToGivingStatement
 *       | addCorrespondingStatement
 *   ) onSizeErrorPhrase? notOnSizeErrorPhrase? END_ADD?
 *
 * @author flaechsig
 *
 */
class AddStatementVisitor  extends Cobol85BaseVisitor<AddStatementNode> {

	@Override public AddStatementNode visitAddStatement(Cobol85Parser.AddStatementContext ctx) {
		AddImplNode add = null;						// mandatory
		SizePhraseNode errorPhrase = null;		// optional
		SizePhraseNode successPhrase = null; 	// optional
		
		if(ctx.addToStatement() != null) {
			add = ctx.addToStatement().accept(new AddToStatementVisitor());
		} else if( ctx.addToGivingStatement() != null) {
			add = ctx.addToGivingStatement().accept(new AddToGivingVisitor());
		} else if( ctx.addCorrespondingStatement() != null) {
			throw new RuntimeException("Nicht implementiert");
		}
		
		if(ctx.onSizeErrorPhrase() != null) {
			errorPhrase = ctx.onSizeErrorPhrase().accept(new SizePhraseVisitor());
		}
		
		if(ctx.notOnSizeErrorPhrase() != null) {
			successPhrase = ctx.notOnSizeErrorPhrase().accept(new SizePhraseVisitor());
		}
		add.setSizeErrorCheck(errorPhrase!=null || successPhrase!=null);
	
		return new AddStatementNode(add, errorPhrase, successPhrase);
	}
}
