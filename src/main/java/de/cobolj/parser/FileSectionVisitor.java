package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85Parser.FileSectionContext;

/**
 * fileSection:
 *     FILE SECTION DOT_FS fileDescriptionEntry*
 *     
 * @author flaechsig
 *
 */
public class FileSectionVisitor extends Cobol85BaseVisitor<FileSectionNode> {
	
	@Override
	public FileSectionNode visitFileSection(FileSectionContext ctx) {
		List<FileDescriptionEntryNode> entries = ctx.fileDescriptionEntry()
				.stream()
				.map(result -> result.accept(new FileDescriptionEntryVisitor()))
				.collect(Collectors.toList());
		return new FileSectionNode(entries);
	}

}
