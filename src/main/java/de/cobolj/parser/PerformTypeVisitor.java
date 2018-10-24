package de.cobolj.parser;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PerformStatementNode;
import de.cobolj.nodes.PerformTimesNode;
import de.cobolj.nodes.PerformTypeNode;
import de.cobolj.parser.Cobol85Parser.PerformTypeContext;

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
			ExpressionNode condition = ctx.performTimes().accept(new PerfomTimesVistor());
			return new PerformTimesNode(condition, perform);
		} else if(ctx.performUntil() != null) {
			throw new RuntimeException("Not implemented");
		} else /* ctx.performVarying() */{
			throw new RuntimeException("Not implemented");
		}
	}
}
