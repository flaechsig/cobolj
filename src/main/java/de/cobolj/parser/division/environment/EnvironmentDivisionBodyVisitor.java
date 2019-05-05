package de.cobolj.parser.division.environment;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.division.environtment.EnvironmentDivisionBodyNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.EnvironmentDivisionBodyContext;
import de.cobolj.parser.division.procedure.InputOutputSectionVisitor;

/**
 * environmentDivisionBody: configurationSection | specialNamesParagraph |
 * inputOutputSection
 * 
 * @author flaechsig
 *
 */
public class EnvironmentDivisionBodyVisitor extends Cobol85BaseVisitor<EnvironmentDivisionBodyNode> {

	@Override
	public EnvironmentDivisionBodyNode visitEnvironmentDivisionBody(EnvironmentDivisionBodyContext ctx) {
		notImplemented(ctx.configurationSection());
		notImplemented(ctx.specialNamesParagraph());

		EnvironmentDivisionBodyNode result = accept(ctx.inputOutputSection(),
				new InputOutputSectionVisitor());

		return result;
	}

}
