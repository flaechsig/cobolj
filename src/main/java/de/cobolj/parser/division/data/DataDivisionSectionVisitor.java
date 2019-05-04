package de.cobolj.parser.division.data;

import de.cobolj.nodes.DataDivisionSectionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.ParserHelper;

/**
 * 
 * dataDivisionSection: fileSection | dataBaseSection | workingStorageSection |
 * linkageSection | communicationSection | localStorageSection | screenSection |
 * reportSection | programLibrarySection;
 * 
 * @author flaechsig
 *
 */
public class DataDivisionSectionVisitor extends Cobol85BaseVisitor<DataDivisionSectionNode> {

	public static DataDivisionSectionVisitor INSTANCE = new DataDivisionSectionVisitor();

	private DataDivisionSectionVisitor() {
	}

	@Override
	public DataDivisionSectionNode visitDataDivisionSection(Cobol85Parser.DataDivisionSectionContext ctx) {
		ParserHelper.notImplemented(ctx.dataBaseSection());
//		ParserHelper.notImplemented(ctx.linkageSection());
		ParserHelper.notImplemented(ctx.communicationSection());
		ParserHelper.notImplemented(ctx.localStorageSection());
		ParserHelper.notImplemented(ctx.screenSection());
		ParserHelper.notImplemented(ctx.reportSection());
		ParserHelper.notImplemented(ctx.programLibrarySection());
		
		DataDivisionSectionNode result = null;
		if(ctx.workingStorageSection() != null) {
			result = ctx.workingStorageSection().accept(new WorkingStorageSectionVisitor());
		}
		if(ctx.fileSection() != null){
			result = ctx.fileSection().accept(new FileSectionVisitor());
		} 
		if(ctx.linkageSection() != null) {
			result = ctx.linkageSection().accept(new LinkageSectionVisitor());
		}
		return result;
	}
}
