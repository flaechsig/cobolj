package de.cobolj.parser.statement.close;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.ParserHelper;
import de.cobolj.parser.Cobol85Parser.CloseFileContext;
import de.cobolj.statement.close.CloseFileNode;

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
