package de.cobolj.parser.division.procedure;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import java.util.List;

import de.cobolj.nodes.PictureNode;
import de.cobolj.nodes.ProcedureDivisionBodyNode;
import de.cobolj.nodes.ProcedureDivisionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.IdentifierVisitor;

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
		notImplemented(ctx.procedureDivisionGivingClause());
		notImplemented(ctx.procedureDeclaratives());

		List<PictureNode> usingDatanames = accept(ctx.usingDataName, IdentifierVisitor.INSTANCE);
		ProcedureDivisionBodyNode bodyNode = accept(ctx.procedureDivisionBody(), new ProcedureDivisionBodyVisitor());

		return new ProcedureDivisionNode(usingDatanames, bodyNode);
	}
}
