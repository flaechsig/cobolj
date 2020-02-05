package de.cobolj.parser.division.procedure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.ProcedureDivisionBodyNode;
import de.cobolj.nodes.StructureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.ParserHelper;

/**
 * Die Anweisungen des Cobol-Programms
 * 
 * procedureDivisionBody: paragraphs procedureSection*;
 * 
 * @author flaechsig
 *
 */
public class ProcedureDivisionBodyVisitor extends Cobol85BaseVisitor<ProcedureDivisionBodyNode> {

	@Override
	public ProcedureDivisionBodyNode visitProcedureDivisionBody(Cobol85Parser.ProcedureDivisionBodyContext ctx) {
		List<StructureNode> paragraphsOrProcedureSection = new ArrayList<>();
		paragraphsOrProcedureSection.add(ParserHelper.accept(ctx.paragraphs(), new ParagraphsVisitor()));

		paragraphsOrProcedureSection.addAll(ctx.procedureSection().stream()
				.map(para -> para.accept(new ProcedureSectionVisitor())).collect(Collectors.toList()));

		return new ProcedureDivisionBodyNode(paragraphsOrProcedureSection);
	}
}
