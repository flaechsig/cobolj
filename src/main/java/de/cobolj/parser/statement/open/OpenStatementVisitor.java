package de.cobolj.parser.statement.open;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.ParserHelper;
import de.cobolj.parser.Cobol85Parser.OpenStatementContext;
import de.cobolj.statement.open.OpenStatementElementNode;
import de.cobolj.statement.open.OpenStatementNode;

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
		ParserHelper.notImplemented(ctx.openIOStatement());
		ParserHelper.notImplemented(ctx.openExtendStatement());

		List<OpenStatementElementNode> openElements = new ArrayList<>();
		if (ctx.openInputStatement() != null) {
			openElements.addAll(ctx.openInputStatement().stream()
					.map(result -> result.accept(new OpenInputStatementVisitor())).collect(Collectors.toList()));
		}
		if (ctx.openOutputStatement() != null) {
			openElements.addAll(ctx.openOutputStatement().stream()
					.map(result -> result.accept(new OpenOutputStatementVisitor())).collect(Collectors.toList()));
		}
		return new OpenStatementNode(openElements);
	}
}
