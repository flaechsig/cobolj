package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

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
		List<InputOutputSectionParagraphNode> paragraphs =  ctx.inputOutputSectionParagraph()
				.stream()
				.map(result -> result.accept(new InputOutputSectioinParagraphVisitor()))
				.collect(Collectors.toList());
		return new InputOutputSecionNode(paragraphs);
	}
}
