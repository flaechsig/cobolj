package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.division.data.DataDescriptionEntryNode;
import de.cobolj.nodes.WorkingStorageSectionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;

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
		
		List<DataDescriptionEntryNode> dataDescriptionEntryNodes = accept(ctx.dataDescriptionEntry(),
				DataDescriptionEntryVisitor.INSTANCE);

		return new WorkingStorageSectionNode(dataDescriptionEntryNodes);
	}
}
