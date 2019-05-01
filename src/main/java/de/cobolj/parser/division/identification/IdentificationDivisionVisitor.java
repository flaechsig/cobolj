package de.cobolj.parser.division.identification;

import de.cobolj.division.identification.ProgramIdParagraphNode;
import de.cobolj.division.identification.IdentificationDivisionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.ParserHelper;
import de.cobolj.parser.Cobol85Parser.IdentificationDivisionContext;

/**
 * identificationDivision : ( IDENTIFICATION | ID ) DIVISION DOT_FS
 * programIdParagraph identificationDivisionBody*
 * 
 * @author flaechsig
 *
 */
public class IdentificationDivisionVisitor extends Cobol85BaseVisitor<IdentificationDivisionNode> {
	@Override
	public IdentificationDivisionNode visitIdentificationDivision(IdentificationDivisionContext ctx) {
		ParserHelper.notImplemented(ctx.identificationDivisionBody());
		
		ProgramIdParagraphNode programIdParagraph = ctx.programIdParagraph().accept(new ProgramIdParagraphVisitor());
		return new IdentificationDivisionNode(programIdParagraph);
	}

}
