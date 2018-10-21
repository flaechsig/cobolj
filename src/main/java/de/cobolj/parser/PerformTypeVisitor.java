package de.cobolj.parser;

import java.util.List;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PerformTimesNode;
import de.cobolj.nodes.PerformTypeNode;
import de.cobolj.parser.Cobol85Parser.PerformTypeContext;
import de.cobolj.statements.StatementNode;

/**
 * performType: performTimes  | performUntil  | performVarying
 * 
 * @author flaechsig
 *
 */
public class PerformTypeVisitor extends Cobol85BaseVisitor<PerformTypeNode>{
	/** Liste der Statements, die durch das PERFORM abgearbeitet werden. */
	private List<StatementNode> statements;

	public PerformTypeVisitor(List<StatementNode> statements) {
		this.statements = statements;
	}

	@Override
	public PerformTypeNode visitPerformType(PerformTypeContext ctx) {
		if(ctx.performTimes() != null) {
			ExpressionNode condition = ctx.performTimes().accept(new PerfomTimesVistor());
			return new PerformTimesNode(condition, statements);
		} else if(ctx.performUntil() != null) {
			throw new RuntimeException("Not implemented");
		} else /* ctx.performVarying() */{
			throw new RuntimeException("Not implemented");
		}
	}
}
