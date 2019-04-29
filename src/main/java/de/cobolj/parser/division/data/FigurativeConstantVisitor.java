package de.cobolj.parser.division.data;

import de.cobolj.nodes.LiteralNode;
import de.cobolj.nodes.LongNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
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
		if(ctx.ZERO()!=null||ctx.ZEROS()!=null||ctx.ZEROES()!=null) {
			return new LongNode(0);
		} else {
			throw new RuntimeException("Not Implemented");
		}
	}
}
