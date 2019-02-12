package de.cobolj.parser;

import de.cobolj.parser.Cobol85Parser.EnvironmentDivisionBodyContext;

/**
 * environmentDivisionBody:
 *     configurationSection | specialNamesParagraph | inputOutputSection
 *     
 * @author flaechsig
 *
 */
public class EnvironmentDivisionBodyVisitor extends Cobol85BaseVisitor<EnvironmentDivisionBodyNode> {
	
	@Override
	public EnvironmentDivisionBodyNode visitEnvironmentDivisionBody(EnvironmentDivisionBodyContext ctx) {
		if(ctx.inputOutputSection()!=null) {
			return ctx.inputOutputSection().accept(new InputOutputSectionVisitor());
		} else {
			throw new RuntimeException("Not Implemented");
		}
	}

}
