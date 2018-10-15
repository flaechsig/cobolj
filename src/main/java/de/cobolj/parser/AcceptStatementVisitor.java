package de.cobolj.parser;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.statements.ChangeElementaryItemNodeGen;
import de.cobolj.statements.accept.AcceptStatementNode;
import de.cobolj.statements.accept.InputNode;
import de.cobolj.statements.accept.StandardInputNode;

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
		FrameSlot slot = ctx.identifier().accept(IdentifierVisitor.INSTANCE);

		Cobol85BaseVisitor<InputNode> visitor = null;
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
		return new AcceptStatementNode(ChangeElementaryItemNodeGen.create(input, slot, false));
	}
}
