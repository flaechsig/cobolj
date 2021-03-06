package de.cobolj.parser.statement.add;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.ResultIdentifierContext;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.statement.CalculationResult;

/**
 * 
 * resultIdentifier: identifier ROUNDED?
 * 
 * @author flaechsig
 *
 */
public class ResultIdentifierVisitor extends Cobol85BaseVisitor<CalculationResult> {

	@Override
	public CalculationResult visitResultIdentifier(ResultIdentifierContext ctx) {
		PictureNode slot = ctx.identifier().accept(IdentifierVisitor.INSTANCE);
		boolean rounded = (ctx.ROUNDED() != null);
		
		return new CalculationResult(slot,  rounded);
	}
}
