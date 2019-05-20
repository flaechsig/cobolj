package de.cobolj.parser.statement.open;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.OpenOutputContext;
import de.cobolj.parser.CobolWordVisitor;
import de.cobolj.statement.open.OpenOutputNode;

/**
 * openInput : fileName=identifier ( REVERSED | WITH? NO REWIND )? ;
 * 
 * 
 * 
 * @author flaechsig
 *
 */
public class OpenOutputVisitor extends Cobol85BaseVisitor<OpenOutputNode> {
	@Override
	public OpenOutputNode visitOpenOutput(OpenOutputContext ctx) {
		String fileName = ctx.fileName.accept(CobolWordVisitor.INSTANCE);
		return new OpenOutputNode(fileName);
	}
}
