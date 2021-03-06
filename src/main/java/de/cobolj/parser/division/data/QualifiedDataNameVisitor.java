package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;

/**
 * 
 * qualifiedDataName: qualifiedDataNameFormat1 | qualifiedDataNameFormat2 |
 * qualifiedDataNameFormat3 | qualifiedDataNameFormat4
 * 
 * @author flaechsig
 *
 */
public class QualifiedDataNameVisitor extends Cobol85BaseVisitor<PictureNode> {

	public static QualifiedDataNameVisitor INSTANCE = new QualifiedDataNameVisitor();

	private QualifiedDataNameVisitor() {}

	@Override
	public PictureNode visitQualifiedDataName(Cobol85Parser.QualifiedDataNameContext ctx) {
		notImplemented(ctx.qualifiedDataNameFormat2());
		notImplemented(ctx.qualifiedDataNameFormat3());
		notImplemented(ctx.qualifiedDataNameFormat4());
		
		PictureNode result = null;
		result = accept(ctx.qualifiedDataNameFormat1(), QualifiedDataNameFormat1Visitor.INSTANCE);
		
		return result;
	}
}
