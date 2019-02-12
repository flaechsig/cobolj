package de.cobolj.parser;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.parser.Cobol85Parser.OpenInputContext;

/**
 * openInput : fileName=identifier ( REVERSED | WITH? NO REWIND )? ;
 * 
 * @author flaechsig
 *
 */
public class OpenInputVisitor extends Cobol85BaseVisitor<OpenInputNode> {
	@Override
	public OpenInputNode visitOpenInput(OpenInputContext ctx) {
		ParserHelper.notImplemented(ctx.REVERSED());
		ParserHelper.notImplemented(ctx.REWIND());
		
		FrameSlot fileName = ctx.fileName.accept(IdentifierVisitor.INSTANCE);
		return new OpenInputNode(fileName);
	}
}
