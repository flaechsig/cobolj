package de.cobolj.parser;

import de.cobolj.parser.Cobol85Parser.ProcedureNameContext;

/**
 * 
 * procedureName: paragraphName inSection? | sectionName
 * 
 * @author flaechsig
 *
 */
public class ProcedureNameVisitor extends Cobol85BaseVisitor<String> {

	@Override
	public String visitProcedureName(ProcedureNameContext ctx) {
		if (ctx.inSection() != null) {
			throw new RuntimeException("Not supported yet");
		}
		if (ctx.paragraphName() != null) {
			return ctx.paragraphName().getText();
		} else {
			return ctx.sectionName().getText();
		}
	}
}
