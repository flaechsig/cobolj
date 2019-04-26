package de.cobolj.parser;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.parser.Cobol85Parser.OpenInputContext;
import de.cobolj.statement.open.OpenInputNode;

/**
 * openInput : fileName=identifier ;
 * 
 * 
 * 
 * @author flaechsig
 *
 */
public class OpenInputVisitor extends Cobol85BaseVisitor<OpenInputNode> {
	@Override
	public OpenInputNode visitOpenInput(OpenInputContext ctx) {
		String fileName = ctx.fileName.accept(IdentifierVisitor.INSTANCE);
		return new OpenInputNode(fileName);
	}
}
