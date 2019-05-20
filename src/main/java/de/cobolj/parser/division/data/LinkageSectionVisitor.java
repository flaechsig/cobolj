package de.cobolj.parser.division.data;

import java.util.List;

import de.cobolj.division.data.DataDescriptionEntryNode;
import de.cobolj.division.data.LinkageSectionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.LinkageSectionContext;
import de.cobolj.parser.ParserHelper;

/**
 * linkageSection : LINKAGE SECTION DOT_FS dataDescriptionEntry*
 * 
 * @author flaechsig
 *
 */
public class LinkageSectionVisitor extends Cobol85BaseVisitor<LinkageSectionNode> {
	@Override
	public LinkageSectionNode visitLinkageSection(LinkageSectionContext ctx) {
		List<DataDescriptionEntryNode> dataDescriptionEntryNodes = ParserHelper.accept(ctx.dataDescriptionEntry(),
				DataDescriptionEntryVisitor.INSTANCE);

		return new LinkageSectionNode(dataDescriptionEntryNodes);
	}
}
