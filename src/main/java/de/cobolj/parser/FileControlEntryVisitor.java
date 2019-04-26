package de.cobolj.parser;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.parser.Cobol85Parser.FileControlEntryFormat1Context;
import de.cobolj.parser.Cobol85Parser.FileControlEntryFormat4Context;

/**
 * Verarbeitet die Formate 1 bis 4 (entsprechend Handbuch)
 * 
 * @author flaechsig
 *
 */
public class FileControlEntryVisitor extends Cobol85BaseVisitor<FileControlEntryNode> {

	/**
	 * fileControlEntryFormat1 : SELECT OPTIONAL? fileName=IDENTIFIER assignClause
	 * reserveClause? organizationClause? paddingCharacterClause?
	 * recordDelimiterClause? accessModeClause? fileStatusClause?
	 */
	@Override
	public FileControlEntryNode visitFileControlEntryFormat1(FileControlEntryFormat1Context ctx) {
		ParserHelper.notImplemented(ctx.OPTIONAL());
		ParserHelper.notImplemented(ctx.reserveClause());
		ParserHelper.notImplemented(ctx.organizationClause());
		ParserHelper.notImplemented(ctx.paddingCharacterClause());
		ParserHelper.notImplemented(ctx.recordDelimiterClause());
		ParserHelper.notImplemented(ctx.accessModeClause());
		ParserHelper.notImplemented(ctx.fileStatusClause());

		String fileName = ctx.fileName.accept(IdentifierVisitor.INSTANCE);
		AssignClauseNode assign = ctx.assignClause().accept(new AssignClauseVisitor());

		return new FileControlEntryNode(fileName, assign);
	}

	/**
	 * 
	 * fileControlEntryFormat4 : SELECT fileName=identifier assignClause
	 */
	@Override
	public FileControlEntryNode visitFileControlEntryFormat4(FileControlEntryFormat4Context ctx) {
		String fileName = ctx.fileName.accept(IdentifierVisitor.INSTANCE);
		AssignClauseNode assign = ctx.assignClause().accept(new AssignClauseVisitor());

		return new FileControlEntryNode(fileName, assign);
	}
}
