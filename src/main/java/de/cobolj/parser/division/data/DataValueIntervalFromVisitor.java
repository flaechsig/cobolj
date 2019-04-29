package de.cobolj.parser.division.data;

import de.cobolj.nodes.LiteralNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.CobolWordVisitor;
import de.cobolj.parser.Cobol85Parser.DataValueIntervalFromContext;

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
