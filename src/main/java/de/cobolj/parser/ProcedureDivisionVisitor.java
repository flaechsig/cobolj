package de.cobolj.parser;

import de.cobolj.nodes.ProcedureDivisionBodyNode;
import de.cobolj.nodes.ProcedureDivisionNode;

/**
 * Die Procedure Division enthält die Ablauf-Logik eines Cobol-Programms
 * 
 * @author flaechsig
 *
 */
public class ProcedureDivisionVisitor extends Cobol85BaseVisitor<ProcedureDivisionNode> {

	@Override
	public ProcedureDivisionNode visitProcedureDivision(Cobol85Parser.ProcedureDivisionContext ctx) {
		// FIXME: PROCEDURE DIVISION procedureDivisionUsingClause?  procedureDivisionGivingClause? DOT_FS procedureDeclaratives?  procedureDivisionBody
		// FIXME: Bisher nur der Body umgesetzt
		ProcedureDivisionBodyVisitor visitor = new ProcedureDivisionBodyVisitor();
		ProcedureDivisionBodyNode bodyNode = ctx.procedureDivisionBody().accept(visitor);
		
		ProcedureDivisionNode division = new ProcedureDivisionNode();
		division.setBody(bodyNode);
		
		return division;
	}
}
