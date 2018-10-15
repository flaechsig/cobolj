package de.cobolj.parser;

import de.cobolj.nodes.StringNode;

/**
 * 
 * dataName: cobolWord
 * 
 * @author flaechsig
 *
 */
public class DataNameVisitor extends Cobol85BaseVisitor<StringNode> {
	
	public static DataNameVisitor INSTANCE = new DataNameVisitor();
	
	private DataNameVisitor() {	}

	@Override
	public StringNode visitDataName(Cobol85Parser.DataNameContext ctx) {
		return ctx.cobolWord().accept(CobolWordVisitor.INSTANCE);
	}
}
