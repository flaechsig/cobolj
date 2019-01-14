package de.cobolj.parser.statement.perform;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformTypeContext;
import de.cobolj.statement.perform.PerformTypeNode;

/**
 * performType: performTimes | performUntil | performVarying
 * 
 * @author flaechsig
 *
 */
public class PerformTypeVisitor extends Cobol85BaseVisitor<PerformTypeNode>{
	/** Auszuführendes Perform-Statement */
	private ExpressionNode perform;

	public PerformTypeVisitor(ExpressionNode perform) {
		this.perform = perform;
	}

	@Override
	public PerformTypeNode visitPerformType(PerformTypeContext ctx) {
		if(ctx.performTimes() != null) {
			return ctx.performTimes().accept(new PerfomTimesVistor(perform));
		} else if(ctx.performUntil() != null) {
			return ctx.performUntil().accept(new PerformUntilVisitor(perform));
		} else /* ctx.performVarying() */{
			return ctx.performVarying().accept(new PerformVaryingVisitor(perform));
		}
	}
}
