package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85Parser.CloseStatementContext;

/**
 * closeStatement : CLOSE closeFile+ ;
 * 
 * @author flaechsig
 *
 */
public class CloseStatementVisitor extends Cobol85BaseVisitor<CloseStatementNode> {
	@Override
	public CloseStatementNode visitCloseStatement(CloseStatementContext ctx) {
		List<CloseFileNode> fileNodes = ctx.closeFile().stream().map(result -> result.accept(new CloseFileVisitor()))
				.collect(Collectors.toList());
		return new CloseStatementNode(fileNodes);
	}

}
