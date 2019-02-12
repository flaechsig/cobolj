package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85Parser.OpenInputStatementContext;

/**
 * openInputStatement : INPUT openInput+
 * 
 * @author flaechsig
 *
 */
public class OpentInputStatementVisitor extends Cobol85BaseVisitor<OpenStatementElementNode> {
	@Override
	public OpenStatementElementNode visitOpenInputStatement(OpenInputStatementContext ctx) {
		List<OpenInputNode> inputs = ctx.openInput().stream().map(result -> result.accept(new OpenInputVisitor()))
				.collect(Collectors.toList());
		return new OpentInputStatementElementNode(inputs);
	}
}
