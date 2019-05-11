package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.division.data.DataOccursClause;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.DataOccursClauseContext;

/**
 * dataOccursClause : OCCURS integerLiteral dataOccursTo? TIMES? ( DEPENDING ON?
 * qualifiedDataName )? dataOccursSort* ( INDEXED BY? LOCAL? indexName+ )? ;
 * 
 * @author flaechsig
 *
 */
public class DataOccursClauseVisitor extends Cobol85BaseVisitor<DataOccursClause> {
	@Override
	public DataOccursClause visitDataOccursClause(DataOccursClauseContext ctx) {
		notImplemented(ctx.dataOccursTo());
		notImplemented(ctx.qualifiedDataName());
		notImplemented(ctx.dataOccursSort());
		notImplemented(ctx.LOCAL());
		notImplemented(ctx.indexName());
		
		int integerLiteral = Integer.valueOf(accept(ctx.numericLiteral(), new NumericalLiteralVisitor()).toString());
		
		return new DataOccursClause(integerLiteral);
	}

}
