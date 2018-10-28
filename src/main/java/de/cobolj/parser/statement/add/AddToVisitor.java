package de.cobolj.parser.statement.add;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.AddGivingContext;
import de.cobolj.parser.statement.CalculationResult;
import de.cobolj.parser.IdentifierVisitor;

/**
 * 
 * addTo: identifier ROUNDED?
 * 
 * @author flaechsig
 *
 */
public class AddToVisitor extends Cobol85BaseVisitor<CalculationResult> {


	@Override public CalculationResult visitAddTo(Cobol85Parser.AddToContext ctx) {
		FrameSlot slot = ctx.identifier().accept(IdentifierVisitor.INSTANCE);
		boolean rounded = (ctx.ROUNDED() != null);
		
		return new CalculationResult(slot,  rounded);
	}
	
	@Override
	public CalculationResult visitAddGiving(AddGivingContext ctx) {
		FrameSlot slot = ctx.identifier().accept(IdentifierVisitor.INSTANCE);
		boolean rounded = (ctx.ROUNDED() != null);
		
		return new CalculationResult(slot,  rounded);
	}
}
