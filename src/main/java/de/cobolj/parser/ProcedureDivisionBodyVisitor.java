package de.cobolj.parser;

import java.util.ArrayList;
import java.util.List;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.ProcedureDivisionBodyNode;

/**
 * Die Anweisungen des Cobol-Programms
 * 
 * procedureDivisionBody:  paragraphs procedureSection*; 
 * 
 * @author flaechsig
 *
 */
public class ProcedureDivisionBodyVisitor extends Cobol85BaseVisitor<ProcedureDivisionBodyNode> {

	@Override
	public ProcedureDivisionBodyNode visitProcedureDivisionBody(Cobol85Parser.ProcedureDivisionBodyContext ctx) {
		// Fixme: Vervollständigen
		List<CobolNode> paragraphsOrProcedureSection = new ArrayList<>();
		ParagraphsVisitor visitor = new ParagraphsVisitor();
		paragraphsOrProcedureSection.add(ctx.paragraphs().accept(visitor));

		return new ProcedureDivisionBodyNode(paragraphsOrProcedureSection);
	}
}
