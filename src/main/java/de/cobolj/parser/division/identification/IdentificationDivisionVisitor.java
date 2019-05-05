package de.cobolj.parser.division.identification;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.division.identification.IdentificationDivisionNode;
import de.cobolj.division.identification.ProgramIdParagraphNode;
import de.cobolj.parser.Cobol85BaseVisitor;
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
		notImplemented(ctx.identificationDivisionBody());
		
		ProgramIdParagraphNode programIdParagraph = accept(ctx.programIdParagraph(), new ProgramIdParagraphVisitor());
		return new IdentificationDivisionNode(programIdParagraph);
	}

}
