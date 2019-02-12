package de.cobolj.parser;

import de.cobolj.nodes.DataDivisionSectionNode;

/**
 * 
 * dataDivisionSection: fileSection | dataBaseSection | workingStorageSection | linkageSection | communicationSection | localStorageSection | screenSection | reportSection | programLibrarySection;
 * 
 * @author flaechsig
 *
 */
public class DataDivisionSectionVisitor extends Cobol85BaseVisitor<DataDivisionSectionNode> {
	
	public static DataDivisionSectionVisitor INSTANCE = new DataDivisionSectionVisitor();
	
	private DataDivisionSectionVisitor() {}

	@Override
	public DataDivisionSectionNode visitDataDivisionSection(Cobol85Parser.DataDivisionSectionContext ctx) {
		DataDivisionSectionNode result = null;
		if(ctx.workingStorageSection() != null) {
			result = ctx.workingStorageSection().accept(new WorkingStorageSectionVisitor());
		} else if (ctx.fileSection() != null){
			result = ctx.fileSection().accept(new FileSectionVisitor());
		} else {
			ParserHelper.notImplemented();
		}
		return result;
	}
}
