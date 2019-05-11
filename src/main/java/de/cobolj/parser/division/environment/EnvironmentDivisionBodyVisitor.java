package de.cobolj.parser.division.environment;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.division.environment.ConfigurationSectionNode;
import de.cobolj.division.environment.SpecialNamesParagraphNode;
import de.cobolj.division.environtment.EnvironmentDivisionBodyNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.EnvironmentDivisionBodyContext;
import de.cobolj.parser.division.procedure.InputOutputSectionVisitor;
import de.cobolj.statement.open.InputOutputSectionNode;

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
		notImplemented(ctx.specialNamesParagraph());

		ConfigurationSectionNode configurationSection = accept(ctx.configurationSection(), new ConfigurationSectionVisitor());
		SpecialNamesParagraphNode specialNamesParagraph = accept(ctx.specialNamesParagraph(), new SpecialNamesParagraphVisitor());
		InputOutputSectionNode inputOutputSection = accept(ctx.inputOutputSection(), new InputOutputSectionVisitor());

		return new EnvironmentDivisionBodyNode(configurationSection, specialNamesParagraph, inputOutputSection);
	}

}
