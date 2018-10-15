package de.cobolj.parser;

import org.antlr.v4.runtime.RuleContext;

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
		// FIXME: Vervollständigen
		Cobol85BaseVisitor<?> visitor;
		RuleContext ctx2 = (RuleContext) ctx.getChild(0);
		int rule = ctx2.getRuleIndex();
		switch (rule) {
		case Cobol85Parser.RULE_workingStorageSection:
			visitor = WorkingStorageSectionVisitor.INSTANCE;
			break;
		default:
			throw new RuntimeException("Unbekannte Data Division Section:" + Cobol85Parser.ruleNames[rule]);
		}
		return (DataDivisionSectionNode)ctx2.accept(visitor);
	}
}
