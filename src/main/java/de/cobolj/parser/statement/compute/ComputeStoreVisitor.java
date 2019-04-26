package de.cobolj.parser.statement.compute;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.ComputeStoreContext;
import de.cobolj.parser.statement.CalculationResult;
import de.cobolj.parser.IdentifierVisitor;

/**
 * 
 * computeStore: identifier ROUNDED?
 * 
 * @author flaechsig
 *
 */
public class ComputeStoreVisitor extends Cobol85BaseVisitor<CalculationResult> {
	@Override
	public CalculationResult visitComputeStore(ComputeStoreContext ctx) {
		String slot = ctx.identifier().accept(IdentifierVisitor.INSTANCE);
		boolean rounded = (ctx.ROUNDED() != null);
		
		return new CalculationResult(slot,  rounded);
	}
}
