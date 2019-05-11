package de.cobolj.parser.statement.open;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.OpenInputContext;
import de.cobolj.parser.CobolWordVisitor;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.statement.open.OpenInputNode;

/**
 * openInput : fileName=cobolWord ;
 * 
 * 
 * 
 * @author flaechsig
 *
 */
public class OpenInputVisitor extends Cobol85BaseVisitor<OpenInputNode> {
	@Override
	public OpenInputNode visitOpenInput(OpenInputContext ctx) {
		String fileName = ctx.fileName.accept(CobolWordVisitor.INSTANCE);
		return new OpenInputNode(fileName);
	}
}
