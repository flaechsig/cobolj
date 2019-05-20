package de.cobolj.parser.statement.close;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.CloseFileContext;
import de.cobolj.parser.CobolWordVisitor;
import de.cobolj.parser.ParserHelper;
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

		String fileName = ctx.fileName.accept(CobolWordVisitor.INSTANCE);
		
		return new CloseFileNode(fileName);
	}
}
