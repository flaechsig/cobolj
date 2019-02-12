package de.cobolj.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85Parser.FileControlParagraphContext;

/**
 * fileControlParagraph: FILE_CONTROL (DOT_FS? fileControlEntry)* DOT_FS
 * 
 * @author flaechsig
 *
 */
public class FileControlParagraphVisitor extends Cobol85BaseVisitor<FileControlParagraphNode> {
	@Override
	public FileControlParagraphNode visitFileControlParagraph(FileControlParagraphContext ctx) {
		List<FileControlEntryNode> entries = new ArrayList<>();
				
		entries.addAll(ctx.fileControlEntryFormat1()
				.stream()
				.map(result -> result.accept(new FileControlEntryVisitor()))
				.collect(Collectors.toList()));
		entries.addAll(ctx.fileControlEntryFormat2()
				.stream()
				.map(result -> result.accept(new FileControlEntryVisitor()))
				.collect(Collectors.toList()));
		entries.addAll(ctx.fileControlEntryFormat3()
				.stream()
				.map(result -> result.accept(new FileControlEntryVisitor()))
				.collect(Collectors.toList()));
		entries.addAll(ctx.fileControlEntryFormat4()
				.stream()
				.map(result -> result.accept(new FileControlEntryVisitor()))
				.collect(Collectors.toList()));
		return new FileControlParagraphNode(entries);
	}

}
