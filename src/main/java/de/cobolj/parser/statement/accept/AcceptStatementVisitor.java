package de.cobolj.parser.statement.accept;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.statement.accept.AcceptStatementNode;
import de.cobolj.statement.accept.InputNode;
import de.cobolj.statement.accept.StandardInputNode;

/**
 * 
 * acceptStatement:  ACCEPT identifier
 *     (acceptFromDateStatement
 *     | acceptFromEscapeKeyStatement
 *     | acceptFromMnemonicStatement
 *     | acceptMessageCountStatement
 *     )? onExceptionClause? notOnExceptionClause? END_ACCEPT?
 * @author flaechsig
 *
 */
public class AcceptStatementVisitor extends Cobol85BaseVisitor<AcceptStatementNode> {

	@Override
	public AcceptStatementNode visitAcceptStatement(Cobol85Parser.AcceptStatementContext ctx) {
		String slot = ctx.identifier().accept(IdentifierVisitor.INSTANCE);

		InputNode input = null;
		if(ctx.acceptFromDateStatement() != null) {
			input = ctx.acceptFromDateStatement().accept(new AcceptFromDateStatementVisitor());
		} else if(ctx.acceptFromEscapeKeyStatement() != null) {
			throw new RuntimeException("Nicht implementiert");
		} else if(ctx.acceptFromMnemonicStatement() != null)  {
			throw new RuntimeException("Nicht implementiert");
		} else if(ctx.acceptMessageCountStatement() != null) {
			throw new RuntimeException("Nicht implementiert");
		} else {
			// Kein besonderer Eingabekanal gesetzt -> Stdin
			input = new StandardInputNode();
		}
		return new AcceptStatementNode(input, slot, false);
	}
}
