package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.division.data.DataDescriptionEntryNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;

/**
 * 
 * dataDescriptionEntry : dataDescriptionEntryFormat1 |
 * dataDescriptionEntryFormat2 | dataDescriptionEntryFormat3 |
 * dataDescriptionEntryExecSql
 * 
 * @author flaechsig
 *
 */
public class DataDescriptionEntryVisitor extends Cobol85BaseVisitor<DataDescriptionEntryNode> {

	public static DataDescriptionEntryVisitor INSTANCE = new DataDescriptionEntryVisitor();

	private DataDescriptionEntryVisitor() {
	}

	@Override
	public DataDescriptionEntryNode visitDataDescriptionEntry(Cobol85Parser.DataDescriptionEntryContext ctx) {
		notImplemented(ctx.dataDescriptionEntryFormat2());
		notImplemented(ctx.dataDescriptionEntryFormat3());
		notImplemented(ctx.dataDescriptionEntryExecSql());

		DataDescriptionEntryNode result = accept(ctx.dataDescriptionEntryFormat1(),
				new DataDescriptionEntryFormat1Visitor());

		return result;
	}
}
