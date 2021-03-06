package de.cobolj.parser.division.procedure;

import java.util.List;

import de.cobolj.division.data.InputOutputSectionParagraphNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.InputOutputSectionContext;
import de.cobolj.parser.ParserHelper;
import de.cobolj.statement.open.InputOutputSectionNode;

/**
 * inputOutputSection: 
 * 		INPUT_OUTPUT SECTION DOT_FS inputOutputSectionParagraph*
 * 
 * @author flaechsig
 *
 */
public class InputOutputSectionVisitor extends Cobol85BaseVisitor<InputOutputSectionNode> {
	@Override
	public InputOutputSectionNode visitInputOutputSection(InputOutputSectionContext ctx) {
		List<InputOutputSectionParagraphNode> paragraphs = ParserHelper.accept(ctx.inputOutputSectionParagraph(), new InputOutputSectioinParagraphVisitor());
		return new InputOutputSectionNode(paragraphs);
	}
}
