package de.cobolj.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85Parser.OpenStatementContext;

/**
 * openStatement : OPEN ( openInputStatement | openOutputStatement |
 * openIOStatement | openExtendStatement )+
 * 
 * @author flaechsig
 *
 */
public class OpenStatementVisitor extends Cobol85BaseVisitor<OpenStatementNode> {
	@Override
	public OpenStatementNode visitOpenStatement(OpenStatementContext ctx) {
		ParserHelper.notImplemented(ctx.openOutputStatement());
		ParserHelper.notImplemented(ctx.openIOStatement());
		ParserHelper.notImplemented(ctx.openExtendStatement());
		
		List<OpenStatementElementNode> openElements = new ArrayList<>();
		openElements.addAll(ctx.openInputStatement().stream()
				.map(result -> result.accept(new OpentInputStatementVisitor())).collect(Collectors.toList()));
		
		return new OpenStatementNode(openElements);
	}
}
