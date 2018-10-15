package de.cobolj.parser;

import org.antlr.v4.runtime.RuleContext;

import de.cobolj.nodes.LiteralNode;

public class LiteralVisitor extends Cobol85BaseVisitor<LiteralNode> {
	
	public static LiteralVisitor INSTANCE = new LiteralVisitor();
	
	private LiteralVisitor() {}

	@Override
	public LiteralNode visitLiteral(Cobol85Parser.LiteralContext ctx) {
		RuleContext ctx2 = (RuleContext) ctx.getChild(0);
		int rule = ctx2.getRuleIndex();
		switch (rule) {
		case Cobol85Parser.RULE_nonNumericLiteral:
			return ctx2.accept(new NonNumericalLiteralVisitor());
		case Cobol85Parser.RULE_numericLiteral:
			return ctx2.accept(new NumericalLiteralVisitor());
		default:
			throw new RuntimeException("Unbekanntes Statement :" + Cobol85Parser.ruleNames[rule]);
		}
	}
}
