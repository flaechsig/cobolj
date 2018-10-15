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
public class QualifiedDataNameVisitor extends Cobol85BaseVisitor<StringNode> {

	public static QualifiedDataNameVisitor INSTANCE = new QualifiedDataNameVisitor();

	private QualifiedDataNameVisitor() {}

	@Override
	public StringNode visitQualifiedDataName(Cobol85Parser.QualifiedDataNameContext ctx) {
		// FIXME: Vervollständigen
		if (ctx.qualifiedDataNameFormat1() != null) {
			return ctx.qualifiedDataNameFormat1().accept(QualifiedDataNameFormat1Visitor.INSTANCE);
		} else {
			throw new RuntimeException("Not Implemented");
		}
	}
}
