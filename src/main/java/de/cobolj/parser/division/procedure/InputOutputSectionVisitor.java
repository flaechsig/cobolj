package de.cobolj.parser.division.procedure;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.division.data.InputOutputSectionParagraphNode;
import de.cobolj.division.environtment.EnvironmentDivisionBodyNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.ParserHelper;
import de.cobolj.parser.Cobol85Parser.InputOutputSectionContext;
import de.cobolj.statement.open.InputOutputSecionNode;

/**
 * inputOutputSection: 
 * 		INPUT_OUTPUT SECTION DOT_FS inputOutputSectionParagraph*
 * 
 * @author flaechsig
 *
 */
public class InputOutputSectionVisitor extends Cobol85BaseVisitor<EnvironmentDivisionBodyNode> {
	@Override
	public EnvironmentDivisionBodyNode visitInputOutputSection(InputOutputSectionContext ctx) {
		List<InputOutputSectionParagraphNode> paragraphs = ParserHelper.accept(ctx.inputOutputSectionParagraph(), new InputOutputSectioinParagraphVisitor());
		return new InputOutputSecionNode(paragraphs);
	}
}
