package de.cobolj.parser;

import de.cobolj.nodes.PerformOneTimeNode;
import de.cobolj.nodes.PerformProcedureStatementNode;
import de.cobolj.nodes.PerformStatementNode;
import de.cobolj.nodes.PerformTypeNode;
import de.cobolj.parser.Cobol85Parser.PerformProcedureStatementContext;

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
		
		PerformStatementNode perform = new PerformProcedureStatementNode(startFunction, endFunction);
		return new PerformOneTimeNode(perform);
	}
}
