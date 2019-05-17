package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.nodes.LiteralNode;
import de.cobolj.nodes.LongNode;
import de.cobolj.nodes.StringNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.FigurativeConstantContext;

/**
 * 
 * figurativeConstant : ALL literal | HIGH_VALUE | HIGH_VALUES | LOW_VALUE |
 * LOW_VALUES | NULL | NULLS | QUOTE | QUOTES | SPACE | SPACES | ZERO | ZEROS |
 * ZEROES
 * 
 * @author flaechsig
 *
 */
public class FigurativeConstantVisitor extends Cobol85BaseVisitor<LiteralNode> {
	@Override
	public LiteralNode visitFigurativeConstant(FigurativeConstantContext ctx) {
		notImplemented(ctx.ALL());
		notImplemented(ctx.literal());
		notImplemented(ctx.HIGH_VALUE());
		notImplemented(ctx.HIGH_VALUES());
		notImplemented(ctx.LOW_VALUE());
		notImplemented(ctx.LOW_VALUES());
		notImplemented(ctx.NULL());
		notImplemented(ctx.NULLS());
		notImplemented(ctx.QUOTE());
		
		if(accept(ctx.ZERO())||accept(ctx.ZEROS())||accept(ctx.ZEROES())) {
			return new LongNode(0);
		} else if(accept(ctx.SPACE()) || accept(ctx.SPACES())) {
			return new StringNode("");
		} else {
			return null;
		}
	}
}
