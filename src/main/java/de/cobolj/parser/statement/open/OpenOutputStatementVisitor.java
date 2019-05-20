package de.cobolj.parser.statement.open;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.OpenOutputStatementContext;
import de.cobolj.statement.open.OpenOutputNode;
import de.cobolj.statement.open.OpenOutputStatementElementNode;
import de.cobolj.statement.open.OpenStatementElementNode;

/**
 * openInputStatement : INPUT openInput+
 * 
 * @author flaechsig
 *
 */
public class OpenOutputStatementVisitor extends Cobol85BaseVisitor<OpenStatementElementNode> {
	@Override
	public OpenStatementElementNode visitOpenOutputStatement(OpenOutputStatementContext ctx) {
		List<OpenOutputNode> outputs = ctx.openOutput().stream().map(result -> result.accept(new OpenOutputVisitor()))
				.collect(Collectors.toList());
		return new OpenOutputStatementElementNode(outputs);
	}
}
