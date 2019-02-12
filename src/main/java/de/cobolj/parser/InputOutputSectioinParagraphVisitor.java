package de.cobolj.parser;

import de.cobolj.parser.Cobol85Parser.InputOutputSectionParagraphContext;

/**
 * inputOutputSectionParagraph:
 *     fileControlParagraph  | ioControlParagraph
 *     
 * @author flaechsig
 *
 */
public class InputOutputSectioinParagraphVisitor extends Cobol85BaseVisitor<InputOutputSectionParagraphNode> {
	@Override
	public InputOutputSectionParagraphNode visitInputOutputSectionParagraph(InputOutputSectionParagraphContext ctx) {
		if(ctx.fileControlParagraph()!=null) {
			return ctx.fileControlParagraph().accept(new FileControlParagraphVisitor());
		} else /* if(ctx.ioControlParagraph() != null) */ {
			throw new RuntimeException("Not Implemented");
		}
	}
}
