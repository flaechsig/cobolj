package de.cobolj.parser.division.environment;

import java.util.List;

import de.cobolj.division.environtment.EnvironmentDivisionBodyNode;
import de.cobolj.division.environtment.EnvironmentDivisionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.EnvironmentDivisionContext;
import de.cobolj.parser.ParserHelper;

/**
 * environmentDivision:
 *     ENVIRONMENT DIVISION DOT_FS environmentDivisionBody*
 *     
 * @author flaechsig
 *
 */
public class EnvironmentDivisionVisitor extends Cobol85BaseVisitor<EnvironmentDivisionNode> {
@Override
public EnvironmentDivisionNode visitEnvironmentDivision(EnvironmentDivisionContext ctx) {
	List<EnvironmentDivisionBodyNode> body = ParserHelper.accept(ctx.environmentDivisionBody(), new EnvironmentDivisionBodyVisitor());
	return new EnvironmentDivisionNode(body);
}
}
