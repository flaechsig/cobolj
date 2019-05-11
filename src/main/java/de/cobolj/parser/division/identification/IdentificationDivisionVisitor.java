package de.cobolj.parser.division.identification;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import java.util.List;

import de.cobolj.division.identification.IdentificationDivisionBodyNode;
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
		ProgramIdParagraphNode programIdParagraph = accept(ctx.programIdParagraph(), new ProgramIdParagraphVisitor());
		List<IdentificationDivisionBodyNode> identificationDivisionBody = accept(ctx.identificationDivisionBody(),
				new IdentificationDivisionBodyVisitor());

		return new IdentificationDivisionNode(programIdParagraph, identificationDivisionBody);
	}

}
