package de.cobolj.parser.statement.close;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.CloseStatementContext;
import de.cobolj.statement.close.CloseFileNode;
import de.cobolj.statement.close.CloseStatementNode;

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
