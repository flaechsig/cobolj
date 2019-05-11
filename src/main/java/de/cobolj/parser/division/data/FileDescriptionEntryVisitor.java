package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.ObjectUtils;

import de.cobolj.division.data.DataDescriptionEntryNode;
import de.cobolj.division.data.FileDescriptionEntryNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.FileDescriptionEntryContext;

/**
 * fileDescriptionEntry: 
 * 	(FD | SD) fileName=IDENTIFIER
 * 		(DOT_FS? fileDescriptionEntryClause)* DOT_FS dataDescriptionEntry*
 * 
 * @author flaechsig
 *
 */
public class FileDescriptionEntryVisitor extends Cobol85BaseVisitor<FileDescriptionEntryNode> {
	@Override
	public FileDescriptionEntryNode visitFileDescriptionEntry(FileDescriptionEntryContext ctx) {
		
		List<FileDescriptionEntryClauseNode> fileDescriptionEntryClause = accept(ctx.fileDescriptionEntryClause(), new FileDescriptionEntryClauseVisitor());
		String desc = ((TerminalNode)ObjectUtils.firstNonNull(ctx.FD(), ctx.SD())).getText();
		String fileName = ctx.fileName.getText();
		List<DataDescriptionEntryNode> dataDescriptionEntries = accept(ctx.dataDescriptionEntry(), DataDescriptionEntryVisitor.INSTANCE);
		
		
		return new FileDescriptionEntryNode(desc, fileName, fileDescriptionEntryClause, dataDescriptionEntries);
	}
}
