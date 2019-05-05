package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.notImplemented;

import org.antlr.v4.runtime.RuleContext;

import de.cobolj.nodes.LiteralNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.LiteralContext;

/**
 * 
 * literal
 *   : nonNumericLiteral     
 *   | figurativeConstant    
 *   | numericLiteral        
 *   | booleanLiteral        
 *   | cicsDfhRespLiteral    
 *   | cicsDfhValueLiteral   
 * 
 * @author flaechsig
 *
 */
public class LiteralVisitor extends Cobol85BaseVisitor<LiteralNode> {
	
	public static LiteralVisitor INSTANCE = new LiteralVisitor();
	
	private LiteralVisitor() {}

	@Override
	public LiteralNode visitLiteral(Cobol85Parser.LiteralContext ctx) {
		notImplemented(ctx.booleanLiteral());
		notImplemented(ctx.cicsDfhRespLiteral());
		notImplemented(ctx.cicsDfhValueLiteral());
		
		RuleContext ctx2 = (RuleContext) ctx.getChild(0);
		int rule = ctx2.getRuleIndex();
		switch (rule) {
		case Cobol85Parser.RULE_nonNumericLiteral:
			return ctx2.accept(new NonNumericalLiteralVisitor());
		case Cobol85Parser.RULE_numericLiteral:
			return ctx2.accept(new NumericalLiteralVisitor());
		case Cobol85Parser.RULE_figurativeConstant:
			return ctx2.accept(new FigurativeConstantVisitor());
		default:
			throw new RuntimeException("Unbekanntes Statement :" + Cobol85Parser.ruleNames[rule]);
		}
	}
}
