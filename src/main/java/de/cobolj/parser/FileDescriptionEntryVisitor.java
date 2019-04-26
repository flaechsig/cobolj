package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.ObjectUtils;

import de.cobolj.parser.Cobol85Parser.FileDescriptionEntryContext;
import de.cobolj.runtime.Picture;

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
		ParserHelper.notImplemented(ctx.fileDescriptionEntryClause());
		
		String desc = ((TerminalNode)ObjectUtils.firstNonNull(ctx.FD(), ctx.SD())).getText();
		String fileName = ctx.fileName.getText();
		List<Picture> dataDescriptionEntries = ctx.dataDescriptionEntry()
				.stream()
				.map(result -> result.accept( DataDescriptionEntryVisitor.INSTANCE) )
				.collect(Collectors.toList());
		
		return new FileDescriptionEntryNode(desc, fileName, dataDescriptionEntries);
	}
}
