package de.cobolj.parser.division.data;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.division.data.LinkageSectionNode;
import de.cobolj.nodes.WorkingStorageSectionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.LinkageSectionContext;
import de.cobolj.runtime.Picture;

/**
 * linkageSection : LINKAGE SECTION DOT_FS dataDescriptionEntry*
 * 
 * @author flaechsig
 *
 */
public class LinkageSectionVisitor extends Cobol85BaseVisitor<LinkageSectionNode> {
	@Override
	public LinkageSectionNode visitLinkageSection(LinkageSectionContext ctx) {
		List<Picture> dataDescriptionEntryNodes = ctx.dataDescriptionEntry()
				.stream()
				.map(dataEntry -> dataEntry.accept(DataDescriptionEntryVisitor.INSTANCE))
				.collect(Collectors.toList());

		return new LinkageSectionNode(dataDescriptionEntryNodes);
	}
}
