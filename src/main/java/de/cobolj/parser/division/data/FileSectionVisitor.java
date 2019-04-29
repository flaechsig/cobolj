package de.cobolj.parser.division.data;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.division.data.FileDescriptionEntryNode;
import de.cobolj.division.data.FileSectionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
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
