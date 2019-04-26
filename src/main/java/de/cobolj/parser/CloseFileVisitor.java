package de.cobolj.parser;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.parser.Cobol85Parser.CloseFileContext;

/**
 * closeFile : fileName=identifier ( closeReelUnitStatement |
 * closeRelativeStatement | closePortFileIOStatement )? ;
 * 
 * @author flaechsig
 *
 */
public class CloseFileVisitor extends Cobol85BaseVisitor<CloseFileNode> {
	@Override
	public CloseFileNode visitCloseFile(CloseFileContext ctx) {
		ParserHelper.notImplemented(ctx.closeReelUnitStatement());
		ParserHelper.notImplemented(ctx.closeRelativeStatement());
		ParserHelper.notImplemented(ctx.closePortFileIOStatement());

		String fileName = ctx.fileName.accept(IdentifierVisitor.INSTANCE);
		
		return new CloseFileNode(fileName);
	}
}
