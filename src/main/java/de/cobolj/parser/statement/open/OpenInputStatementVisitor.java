package de.cobolj.parser.statement.open;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.OpenInputStatementContext;
import de.cobolj.statement.open.OpenInputNode;
import de.cobolj.statement.open.OpenStatementElementNode;
import de.cobolj.statement.open.OpentInputStatementElementNode;

/**
 * openInputStatement : INPUT openInput+
 * 
 * @author flaechsig
 *
 */
public class OpenInputStatementVisitor extends Cobol85BaseVisitor<OpenStatementElementNode> {
	@Override
	public OpenStatementElementNode visitOpenInputStatement(OpenInputStatementContext ctx) {
		List<OpenInputNode> inputs = ctx.openInput().stream().map(result -> result.accept(new OpenInputVisitor()))
				.collect(Collectors.toList());
		return new OpentInputStatementElementNode(inputs);
	}
}
