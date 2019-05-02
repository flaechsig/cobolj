package de.cobolj.parser.division.procedure;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.ProcedureDivisionBodyNode;
import de.cobolj.nodes.ProcedureDivisionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.ParserHelper;

/**
 * procedureDivision : PROCEDURE DIVISION (USING usingDataName+=identifier+)?
 * procedureDivisionGivingClause? DOT_FS procedureDeclaratives?
 * procedureDivisionBody ;
 * 
 * @author flaechsig
 *
 */
public class ProcedureDivisionVisitor extends Cobol85BaseVisitor<ProcedureDivisionNode> {

	@Override
	public ProcedureDivisionNode visitProcedureDivision(Cobol85Parser.ProcedureDivisionContext ctx) {
		ParserHelper.notImplemented(ctx.procedureDivisionGivingClause());
		ParserHelper.notImplemented(ctx.procedureDeclaratives());

		List<String> usingDatanames = ctx.usingDataName.stream().map(result -> result.accept(IdentifierVisitor.INSTANCE))
				.collect(Collectors.toList());
		ProcedureDivisionBodyNode bodyNode = ctx.procedureDivisionBody().accept(new ProcedureDivisionBodyVisitor());

		ProcedureDivisionNode division = new ProcedureDivisionNode(usingDatanames, bodyNode);

		return division;
	}
}
