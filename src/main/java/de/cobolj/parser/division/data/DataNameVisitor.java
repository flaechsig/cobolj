package de.cobolj.parser.division.data;

import de.cobolj.nodes.StringNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.CobolWordVisitor;
import de.cobolj.parser.Cobol85Parser.DataNameContext;

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
