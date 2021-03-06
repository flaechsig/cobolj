package de.cobolj.parser.statement.unstring;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.UnstringIntoContext;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.statement.unstring.UnstringInto;

/**
 * 
 * unstringInto : identifier (DELIMITER IN? delimiter=Inidentifier)? (COUNT IN?
 * countIn=identifier)? ;
 * 
 * @author flaechsig
 *
 */
public class UnstringIntoVisitor extends Cobol85BaseVisitor<UnstringInto> {

	@Override
	public UnstringInto visitUnstringInto(UnstringIntoContext ctx) {
		PictureNode receiving = ctx.receiving.accept(IdentifierVisitor.INSTANCE);
		PictureNode delimiter = null;
		if (ctx.delimiter != null) {
			delimiter = ctx.delimiter.accept(IdentifierVisitor.INSTANCE);
		}
		PictureNode count = null;
		if (ctx.countIn != null) {
			count = ctx.countIn.accept(IdentifierVisitor.INSTANCE);
		}

		return new UnstringInto(receiving, delimiter, count);
	}

}
