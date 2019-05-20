package de.cobolj.parser.division.procedure;

import de.cobolj.division.data.InputOutputSectionParagraphNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.InputOutputSectionParagraphContext;
import de.cobolj.parser.division.data.FileControlParagraphVisitor;

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
