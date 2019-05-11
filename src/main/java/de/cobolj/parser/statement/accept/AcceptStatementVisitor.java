package de.cobolj.parser.statement.accept;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.statement.accept.AcceptStatementNode;
import de.cobolj.statement.accept.InputNode;
import de.cobolj.statement.accept.StandardInputNode;

/**
 * 
 * acceptStatement: ACCEPT identifier (acceptFromDateStatement |
 * acceptFromEscapeKeyStatement | acceptFromMnemonicStatement |
 * acceptMessageCountStatement )? onExceptionClause? notOnExceptionClause?
 * END_ACCEPT?
 * 
 * @author flaechsig
 *
 */
public class AcceptStatementVisitor extends Cobol85BaseVisitor<AcceptStatementNode> {

	@Override
	public AcceptStatementNode visitAcceptStatement(Cobol85Parser.AcceptStatementContext ctx) {
		notImplemented(ctx.acceptFromMnemonicStatement());
		notImplemented(ctx.acceptMessageCountStatement());
		notImplemented(ctx.acceptMessageCountStatement());
		
		PictureNode slot = accept(ctx.identifier(), IdentifierVisitor.INSTANCE);
		
		InputNode input = null;
		input = accept(input, ctx.acceptFromDateStatement(), new AcceptFromDateStatementVisitor());
		
		if(input == null)  {
			// Kein Kanal vorgegeben
			input = new StandardInputNode();
		}
		
		return new AcceptStatementNode(input,slot,false);
	}
}
