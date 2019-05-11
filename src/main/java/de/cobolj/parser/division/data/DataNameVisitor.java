package de.cobolj.parser.division.data;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.CobolWordVisitor;

/**
 * 
 * dataName: cobolWord
 * 
 * @author flaechsig
 *
 */
public class DataNameVisitor extends Cobol85BaseVisitor<PictureNode> {
	
	public static DataNameVisitor INSTANCE = new DataNameVisitor();
	
	private DataNameVisitor() {	}

	@Override
	public PictureNode visitDataName(Cobol85Parser.DataNameContext ctx) {
		return new PictureNode(ctx.cobolWord().accept(CobolWordVisitor.INSTANCE));
	}
}
