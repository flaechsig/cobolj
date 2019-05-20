package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.nodes.LiteralNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.ParserHelper;

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
		
		LiteralNode result = null;
		result = ParserHelper.accept(ctx.nonNumericLiteral(), new NonNumericalLiteralVisitor());
		if(result == null) {
			result = ParserHelper.accept(ctx.numericLiteral(), new NumericalLiteralVisitor());
		}
		if(result == null) {
			result = ParserHelper.accept(ctx.figurativeConstant(), new FigurativeConstantVisitor());
		}
		
		return result;
	}
}
