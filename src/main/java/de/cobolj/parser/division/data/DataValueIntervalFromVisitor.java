package de.cobolj.parser.division.data;

import de.cobolj.nodes.LiteralNode;
import de.cobolj.nodes.StringNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.CobolWordVisitor;

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
			return new StringNode(ctx.cobolWord().accept(CobolWordVisitor.INSTANCE));
		}
	}
}
