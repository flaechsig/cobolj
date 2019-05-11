package de.cobolj.parser.division.environment;

import de.cobolj.division.environment.SpecialNamesParagraphNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.SpecialNamesParagraphContext;
import de.cobolj.parser.ParserHelper;

/**
 * specialNamesParagraph : SPECIAL_NAMES DOT_FS ( specialNameClause+ DOT_FS )? ;
 * 
 * @author flaechsig
 *
 */
public class SpecialNamesParagraphVisitor extends Cobol85BaseVisitor<SpecialNamesParagraphNode> {
	@Override
	public SpecialNamesParagraphNode visitSpecialNamesParagraph(SpecialNamesParagraphContext ctx) {
		ParserHelper.notImplemented(ctx.specialNameClause());
		// FIXME
		return null;
	}

}
