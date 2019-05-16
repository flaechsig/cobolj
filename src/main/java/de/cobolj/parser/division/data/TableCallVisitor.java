package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import java.util.List;

import de.cobolj.division.data.SubscriptNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.TableCallContext;

/**
 * tableCall : qualifiedDataName ( LPARENCHAR subscript ( COMMACHAR? subscript
 * )* RPARENCHAR )* referenceModifier? ;
 * 
 * @author flaechsig
 *
 */
public class TableCallVisitor extends Cobol85BaseVisitor<PictureNode> {
	@Override
	public PictureNode visitTableCall(TableCallContext ctx) {
		notImplemented(ctx.subscript().size() > 1);
		notImplemented(ctx.referenceModifier());
		
		PictureNode qualifiedDataName = accept(ctx.qualifiedDataName(), QualifiedDataNameVisitor.INSTANCE);
		List<SubscriptNode> subscript = accept(ctx.subscript(), new SubscriptVisitor());

		return new PictureNode(qualifiedDataName.getSlot(), subscript);
	}

}
