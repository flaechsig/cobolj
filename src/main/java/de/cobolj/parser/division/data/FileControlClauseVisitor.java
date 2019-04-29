package de.cobolj.parser.division.data;

import de.cobolj.division.data.FileControlClauseNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.ParserHelper;
import de.cobolj.parser.Cobol85Parser.FileControlClauseContext;

/**
 * fileControlClause: assignClause | reserveClause | organizationClause |
 * paddingCharacterClause | recordDelimiterClause | accessModeClause |
 * recordKeyClause | alternateRecordKeyClause | fileStatusClause |
 * passwordClause | relativeKeyClause
 * 
 * @author flaechsig
 *
 */
public class FileControlClauseVisitor extends Cobol85BaseVisitor<FileControlClauseNode> {

	@Override
	public FileControlClauseNode visitFileControlClause(FileControlClauseContext ctx) {
		ParserHelper.notImplemented(ctx.reserveClause());
		ParserHelper.notImplemented(ctx.organizationClause());
		ParserHelper.notImplemented(ctx.paddingCharacterClause());
		ParserHelper.notImplemented(ctx.recordDelimiterClause());
		ParserHelper.notImplemented(ctx.accessModeClause());
		ParserHelper.notImplemented(ctx.recordKeyClause());
		ParserHelper.notImplemented(ctx.alternateRecordKeyClause());
		ParserHelper.notImplemented(ctx.fileStatusClause());
		ParserHelper.notImplemented(ctx.passwordClause());
		ParserHelper.notImplemented(ctx.relativeKeyClause());

		return ctx.accept(new AssignClauseVisitor());
	}

}
