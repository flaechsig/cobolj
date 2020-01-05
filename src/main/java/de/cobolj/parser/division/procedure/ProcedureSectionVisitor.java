package de.cobolj.parser.division.procedure;

import de.cobolj.nodes.ParagraphsNode;
import de.cobolj.nodes.ProcedureSectionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.ProcedureSectionContext;
import de.cobolj.parser.CobolWordVisitor;
import de.cobolj.parser.ParserHelper;

/**
 * procedureSection:	
 *     (sectionName SECTION integerLiteral?) DOT_FS paragraphs;
 *    
 * @author flaechsig
 *
 */
public class ProcedureSectionVisitor extends Cobol85BaseVisitor<ProcedureSectionNode> {
 
	@Override
	public ProcedureSectionNode visitProcedureSection(ProcedureSectionContext ctx) {
		String sectionName = ParserHelper.accept(ctx.sectionName(), CobolWordVisitor.INSTANCE);
		// integerLiteral (Segmentnummer wird ignoriert
		ParagraphsNode paragraphs = ParserHelper.accept(ctx.paragraphs(), new ParagraphsVisitor());
		
		return new ProcedureSectionNode(sectionName, paragraphs);
	}
}
