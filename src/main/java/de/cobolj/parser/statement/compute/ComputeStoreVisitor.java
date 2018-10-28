package de.cobolj.parser.statement.compute;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.ComputeStoreContext;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.statement.add.AddToResult;

/**
 * 
 * computeStore: identifier ROUNDED?
 * 
 * @author flaechsig
 *
 */
public class ComputeStoreVisitor extends Cobol85BaseVisitor<AddToResult> {
	@Override
	public AddToResult visitComputeStore(ComputeStoreContext ctx) {
		FrameSlot slot = ctx.identifier().accept(IdentifierVisitor.INSTANCE);
		boolean rounded = (ctx.ROUNDED() != null);
		
		return new AddToResult(slot,  rounded);
	}
}
