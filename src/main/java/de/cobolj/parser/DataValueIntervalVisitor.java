package de.cobolj.parser;

import de.cobolj.nodes.LiteralNode;

/**
 * 
 * dataValueInterval: dataValueIntervalFrom dataValueIntervalTo?
 * 
 * @author flaechsig
 *
 */
public class DataValueIntervalVisitor extends Cobol85BaseVisitor<LiteralNode> {
	
	public static DataValueIntervalVisitor INSTANCE = new DataValueIntervalVisitor();
	
	private DataValueIntervalVisitor() {	}

	@Override
	public LiteralNode visitDataValueInterval(Cobol85Parser.DataValueIntervalContext ctx) {
		// FIXME
		LiteralNode from = ctx.dataValueIntervalFrom().accept(DataValueIntervalFromVisitor.INSTANCE);
//		LiteralNode to = null;
//		
//		if(ctx.dataValueIntervalTo() != null) {
//			to = ctx.dataValueIntervalTo().accept(DataValueIntervalToVisitor.INSTANCE);
//		}
		
		return from;
	}
}
