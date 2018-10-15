package de.cobolj.parser;

import de.cobolj.nodes.LiteralNode;

/**
 * 
 * dataValueIntervalFrom: literal | cobolWord
 *     
 * @author flaechsig
 *
 */
public class DataValueIntervalFromVisitor extends Cobol85BaseVisitor<LiteralNode> {
	
	public static DataValueIntervalFromVisitor INSTANCE = new DataValueIntervalFromVisitor();
	
	private DataValueIntervalFromVisitor() {}

	@Override
	public LiteralNode visitDataValueIntervalFrom(Cobol85Parser.DataValueIntervalFromContext ctx) {
		if(ctx.literal()!=null) {
			return ctx.literal().accept(LiteralVisitor.INSTANCE);
		} else {
			return ctx.cobolWord().accept(CobolWordVisitor.INSTANCE);
		}
	}
}
