package de.cobolj.parser;

import de.cobolj.nodes.StringNode;

/**
 * 
 * qualifiedDataName: qualifiedDataNameFormat1 | qualifiedDataNameFormat2 |
 * qualifiedDataNameFormat3 | qualifiedDataNameFormat4
 * 
 * @author flaechsig
 *
 */
public class QualifiedDataNameVisitor extends Cobol85BaseVisitor<String> {

	public static QualifiedDataNameVisitor INSTANCE = new QualifiedDataNameVisitor();

	private QualifiedDataNameVisitor() {}

	@Override
	public String visitQualifiedDataName(Cobol85Parser.QualifiedDataNameContext ctx) {
		ParserHelper.notImplemented(ctx.qualifiedDataNameFormat2());
		ParserHelper.notImplemented(ctx.qualifiedDataNameFormat3());
		ParserHelper.notImplemented(ctx.qualifiedDataNameFormat4());
		
		String result = null;
		if (ctx.qualifiedDataNameFormat1() != null) {
			result =  ctx.qualifiedDataNameFormat1().accept(QualifiedDataNameFormat1Visitor.INSTANCE);
		}
		return result;
	}
}
