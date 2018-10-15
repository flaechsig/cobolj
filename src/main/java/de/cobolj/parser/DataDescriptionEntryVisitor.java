package de.cobolj.parser;

import org.antlr.v4.runtime.RuleContext;

import de.cobolj.statements.WriteElementaryItemNode;

/**
 * 
 * dataDescriptionEntry : dataDescriptionEntryFormat1 |
 * dataDescriptionEntryFormat2 | dataDescriptionEntryFormat3 |
 * dataDescriptionEntryExecSql
 * 
 * @author flaechsig
 *
 */
public class DataDescriptionEntryVisitor extends Cobol85BaseVisitor<WriteElementaryItemNode> {
	
	public static DataDescriptionEntryVisitor INSTANCE = new DataDescriptionEntryVisitor();
	
	private DataDescriptionEntryVisitor() {	}

	@Override
	public WriteElementaryItemNode visitDataDescriptionEntry(Cobol85Parser.DataDescriptionEntryContext ctx) {
		Cobol85BaseVisitor<?> visitor;
		RuleContext ctx2 = (RuleContext)ctx.getChild(0);
		int rule = ctx2.getRuleIndex();
		
		switch (rule) {
		// FIXME: Vervollständigen
		case Cobol85Parser.RULE_dataDescriptionEntryFormat1:
			visitor = DataDescriptionEntryFormat1Visitor.INSTANCE;
			break;
		default:
			throw new RuntimeException("Unbekannte Data Division Section:" + Cobol85Parser.ruleNames[rule]);
		}
		return (WriteElementaryItemNode)ctx2.accept(visitor);
	}
}
