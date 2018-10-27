package de.cobolj.parser;

import de.cobolj.parser.Cobol85Parser.PerformTypeContext;
import de.cobolj.statements.perform.PerformStatementNode;
import de.cobolj.statements.perform.PerformTypeNode;

/**
 * performType: performTimes | performUntil | performVarying
 * 
 * @author flaechsig
 *
 */
public class PerformTypeVisitor extends Cobol85BaseVisitor<PerformTypeNode>{
	/** Auszuführendes Perform-Statement */
	private PerformStatementNode perform;

	public PerformTypeVisitor(PerformStatementNode perform) {
		this.perform = perform;
	}

	@Override
	public PerformTypeNode visitPerformType(PerformTypeContext ctx) {
		if(ctx.performTimes() != null) {
			return ctx.performTimes().accept(new PerfomTimesVistor(perform));
		} else if(ctx.performUntil() != null) {
			return ctx.performUntil().accept(new PerformUntilVisitor(perform));
		} else /* ctx.performVarying() */{
			throw new RuntimeException("Not implemented");
		}
	}
}
