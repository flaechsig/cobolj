package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.parser.Cobol85Parser.EnvironmentDivisionContext;

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
	List<EnvironmentDivisionBodyNode> body = ctx.environmentDivisionBody()
			.stream()
			.map(result -> result.accept(new EnvironmentDivisionBodyVisitor()))
			.collect(Collectors.toList());
	
	return new EnvironmentDivisionNode(body);
}
}
