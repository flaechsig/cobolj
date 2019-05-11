package de.cobolj.parser.division.identification;

import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.division.identification.IdentificationDivisionBodyNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.ParserHelper;
import de.cobolj.parser.Cobol85Parser.IdentificationDivisionBodyContext;
import de.cobolj.parser.Cobol85Parser.InstallationParagraphContext;

/**
 * identificationDivisionBody : authorParagraph | installationParagraph |
 * dateWrittenParagraph | dateCompiledParagraph | securityParagraph |
 * remarksParagraph ;
 * 
 * 
 * @author flaechsig
 *
 */
public class IdentificationDivisionBodyVisitor extends Cobol85BaseVisitor<IdentificationDivisionBodyNode> {
	@Override
	public IdentificationDivisionBodyNode visitIdentificationDivisionBody(IdentificationDivisionBodyContext ctx) {
		notImplemented(ctx.authorParagraph());
		notImplemented(ctx.dateWrittenParagraph());
		notImplemented(ctx.dateCompiledParagraph());
		notImplemented(ctx.securityParagraph());
		notImplemented(ctx.remarksParagraph());

		InstallationParagraphNode installationParagraph = (InstallationParagraphNode) ParserHelper
				.accept(ctx.installationParagraph(), this);
		if (installationParagraph != null) {
			return installationParagraph;
		}
		return new IdentificationDivisionBodyNode();
	}

	/**
	 * installationParagraph : INSTALLATION DOT_FS commentEntry? ;
	 */
	@Override
	public IdentificationDivisionBodyNode visitInstallationParagraph(InstallationParagraphContext ctx) {
		
		return new InstallationParagraphNode();
	}
}
