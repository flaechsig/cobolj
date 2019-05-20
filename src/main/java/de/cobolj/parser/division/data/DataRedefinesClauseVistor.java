package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.DataRedefinesClauseContext;

/**
 * dataRedefinesClause : REDEFINES dataName ;
 * 
 * @author flaechsig
 *
 */
public class DataRedefinesClauseVistor extends Cobol85BaseVisitor<PictureNode> {
	@Override
	public PictureNode visitDataRedefinesClause(DataRedefinesClauseContext ctx) {
		return accept(ctx.dataName(), new DataNameVisitor(null));
	}
}
