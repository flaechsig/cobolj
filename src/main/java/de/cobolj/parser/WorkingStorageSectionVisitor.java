package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.WorkingStorageSectionNode;
import de.cobolj.statement.WriteElementaryItemNode;

/**
 * 
 * workingStorageSection: WORKING_STORAGE SECTION DOT_FS dataDescriptionEntry*
 * 
 * @author flaechsig
 *
 */
public class WorkingStorageSectionVisitor extends Cobol85BaseVisitor<WorkingStorageSectionNode> {
	
	public static WorkingStorageSectionVisitor INSTANCE = new WorkingStorageSectionVisitor();
	
	private WorkingStorageSectionVisitor() {}

	@Override
	public WorkingStorageSectionNode visitWorkingStorageSection(Cobol85Parser.WorkingStorageSectionContext ctx) {
		List<WriteElementaryItemNode> dataDescriptionEntryNodes = ctx.dataDescriptionEntry()
				.stream()
				.map(dataEntry -> dataEntry.accept(DataDescriptionEntryVisitor.INSTANCE))
				.collect(Collectors.toList());

		return new WorkingStorageSectionNode(dataDescriptionEntryNodes);
	}
}
