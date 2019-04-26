package de.cobolj.parser;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.parser.Cobol85Parser.OpenOutputContext;
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
		String fileName = ctx.fileName.accept(IdentifierVisitor.INSTANCE);
		return new OpenOutputNode(fileName);
	}
}
