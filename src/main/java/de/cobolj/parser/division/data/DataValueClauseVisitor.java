package de.cobolj.parser.division.data;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.LiteralNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;

/**
 * 
 * dataValueClause: ( VALUE IS? | VALUES ARE? )? dataValueInterval  (COMMACHAR? dataValueInterval)*
 * 
 * @author flaechsig
 *
 */
public class DataValueClauseVisitor extends Cobol85BaseVisitor<LiteralNode> {
	
	public static DataValueClauseVisitor INSTANCE = new DataValueClauseVisitor();
	
	private DataValueClauseVisitor() {	}

	@Override
	public LiteralNode visitDataValueClause(Cobol85Parser.DataValueClauseContext ctx) {
		// FIXME
		List<LiteralNode> interval = ctx.dataValueInterval()
				.stream()
				.map(node -> node.accept(DataValueIntervalVisitor.INSTANCE))
				.collect(Collectors.toList());
		return interval.get(0);
	}
}
