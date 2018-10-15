package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.LiteralNode;

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
