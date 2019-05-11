package de.cobolj.parser.division.procedure;

import java.util.ArrayList;
import java.util.List;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.ProcedureDivisionBodyNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.ParserHelper;
import de.cobolj.parser.Cobol85Parser.ProcedureDivisionBodyContext;

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
		ParserHelper.notImplemented(ctx.procedureSection());
		
		List<CobolNode> paragraphsOrProcedureSection = new ArrayList<>();
		paragraphsOrProcedureSection.add(ParserHelper.accept(ctx.paragraphs(), new ParagraphsVisitor()));


		return new ProcedureDivisionBodyNode(paragraphsOrProcedureSection);
	}
}
