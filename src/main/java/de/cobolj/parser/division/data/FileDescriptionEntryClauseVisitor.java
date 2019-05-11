package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.FileDescriptionEntryClauseContext;
import de.cobolj.parser.Cobol85Parser.FileDescriptionEntryContext;

/**
 * Siehe bei den einzelnen Methoden
 * 
 * @author flaechsig
 *
 */
public class FileDescriptionEntryClauseVisitor extends Cobol85BaseVisitor<FileDescriptionEntryClauseNode> {

	/**
	 * fileDescriptionEntryClause : externalClause | globalClause |
	 * blockContainsClause | recordContainsClause | labelRecordsClause |
	 * valueOfClause | dataRecordsClause | linageClause | codeSetClause |
	 * reportClause | recordingModeClause ;
	 */
	@Override
	public FileDescriptionEntryClauseNode visitFileDescriptionEntryClause(FileDescriptionEntryClauseContext ctx) {
		notImplemented(ctx.externalClause());
		notImplemented(ctx.globalClause());
		notImplemented(ctx.labelRecordsClause());
		notImplemented(ctx.valueOfClause());
		notImplemented(ctx.dataRecordsClause());
		notImplemented(ctx.linageClause());
		notImplemented(ctx.codeSetClause());
		notImplemented(ctx.reportClause());
		notImplemented(ctx.recordingModeClause());
		
		// Folgende Elemente haben keine Bedeutung und werden ignoriert
		// notImplemented(ctx.blockContainsClause());
		// notImplemented(ctx.recordContainsClause());
		
		return new FileDescriptionEntryClauseNode();
	}

}
