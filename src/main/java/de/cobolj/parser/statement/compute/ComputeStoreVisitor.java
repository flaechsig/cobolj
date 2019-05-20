package de.cobolj.parser.statement.compute;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.ComputeStoreContext;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.statement.CalculationResult;

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
		PictureNode slot = ctx.identifier().accept(IdentifierVisitor.INSTANCE);
		boolean rounded = (ctx.ROUNDED() != null);
		
		return new CalculationResult(slot,  rounded);
	}
}
