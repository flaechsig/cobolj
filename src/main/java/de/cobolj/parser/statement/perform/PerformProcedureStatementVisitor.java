package de.cobolj.parser.statement.perform;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.PerformProcedureStatementContext;
import de.cobolj.parser.ProcedureNameVisitor;
import de.cobolj.statements.perform.PerformOneTimeNode;
import de.cobolj.statements.perform.PerformProcedureStatementNode;
import de.cobolj.statements.perform.PerformTypeNode;

/**
 * 
 * performProcedureStatement: procedureName ((THROUGH | THRU) procedureName)? performType?
 * 
 * @author flaechsig
 *
 */
public class PerformProcedureStatementVisitor extends Cobol85BaseVisitor<PerformTypeNode> {

	@Override
	public PerformTypeNode visitPerformProcedureStatement(PerformProcedureStatementContext ctx) {
		ProcedureNameVisitor visitor = new ProcedureNameVisitor();
		String startFunction = ctx.procedureName(0).accept(visitor);
		String endFunction = startFunction;
		
		if(ctx.procedureName(1) != null) {
			endFunction = ctx.procedureName(1).accept(visitor);
		}
		
		ExpressionNode perform = new PerformProcedureStatementNode(startFunction, endFunction);
		PerformTypeNode node;
		if(ctx.performType() != null) {
			node = ctx.performType().accept(new PerformTypeVisitor(perform));
		} else {
			node = new PerformOneTimeNode(perform);
		}
		return node;
	}
}
