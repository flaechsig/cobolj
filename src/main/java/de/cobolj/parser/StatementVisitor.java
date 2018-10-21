package de.cobolj.parser;

import org.antlr.v4.runtime.RuleContext;

import de.cobolj.statements.StatementNode;

public class StatementVisitor extends Cobol85BaseVisitor<StatementNode> {

	@Override
	public StatementNode visitStatement(Cobol85Parser.StatementContext ctx) {
		Cobol85BaseVisitor<?> visitor = null;
		RuleContext ctx2 = (RuleContext) ctx.getChild(0);
		int rule = ctx2.getRuleIndex();
		switch (rule) {
		case Cobol85Parser.RULE_displayStatement:
			visitor = new DisplayStatementVisitor();
			break;
		case Cobol85Parser.RULE_stopStatement:
			visitor = new StopStatementVisitor();
			break;
		case Cobol85Parser.RULE_acceptStatement:
			visitor = new AcceptStatementVisitor();
			break;
		case Cobol85Parser.RULE_addStatement:
			visitor = new AddStatementVisitor();
			break;
		case Cobol85Parser.RULE_moveStatement:
			visitor = new MoveStatementVisitor();
			break;
		case Cobol85Parser.RULE_performStatement:
			visitor = new PerformStatementVisitor();
			break;
		default:
			throw new RuntimeException("Unbekanntes Statement :" + Cobol85Parser.ruleNames[rule]);
		}

		return (StatementNode) ctx2.accept(visitor);
	}
}
