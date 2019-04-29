package de.cobolj.parser.division.data;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.WorkingStorageSectionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.WorkingStorageSectionContext;
import de.cobolj.runtime.Picture;

/**
 * 
 * workingStorageSection: WORKING_STORAGE SECTION DOT_FS dataDescriptionEntry*
 * 
 * @author flaechsig
 *
 */
public class WorkingStorageSectionVisitor extends Cobol85BaseVisitor<WorkingStorageSectionNode> {
	@Override
	public WorkingStorageSectionNode visitWorkingStorageSection(Cobol85Parser.WorkingStorageSectionContext ctx) {
		List<Picture> dataDescriptionEntryNodes = ctx.dataDescriptionEntry()
				.stream()
				.map(dataEntry -> dataEntry.accept(DataDescriptionEntryVisitor.INSTANCE))
				.collect(Collectors.toList());

		return new WorkingStorageSectionNode(dataDescriptionEntryNodes);
	}
}
