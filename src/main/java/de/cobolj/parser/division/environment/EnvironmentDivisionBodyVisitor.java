package de.cobolj.parser.division.environment;

import de.cobolj.division.environtment.EnvironmentDivisionBodyNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.EnvironmentDivisionBodyContext;
import de.cobolj.parser.division.procedure.InputOutputSectionVisitor;

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
