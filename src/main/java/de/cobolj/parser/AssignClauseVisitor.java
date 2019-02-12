package de.cobolj.parser;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.StringNode;
import de.cobolj.parser.Cobol85Parser.AssignClauseContext;
import de.cobolj.util.ExpressionNodeFactory;
import de.cobolj.util.StringLiteral;

/**
 * assignClause : ASSIGN TO? ( DISK | DISPLAY | KEYBOARD | PORT | PRINTER |
 * READER | REMOTE | TAPE | VIRTUAL | assignmentName=IDENTIFIER | literal )
 * 
 * @author flaechsig
 *
 */
public class AssignClauseVisitor extends Cobol85BaseVisitor<AssignClauseNode> {
	
	@Override
	public AssignClauseNode visitAssignClause(AssignClauseContext ctx) {
		ParserHelper.notImplemented(ctx.DISK());
		ParserHelper.notImplemented(ctx.DISPLAY());
		ParserHelper.notImplemented(ctx.KEYBOARD());
		ParserHelper.notImplemented(ctx.PORT());
		ParserHelper.notImplemented(ctx.PRINTER());
		ParserHelper.notImplemented(ctx.READER());
		ParserHelper.notImplemented(ctx.REMOTE());
		ParserHelper.notImplemented(ctx.TAPE());
		ParserHelper.notImplemented(ctx.VIRTUAL());
		
		ExpressionNode node=null;
		if(ctx.assignmentName != null) {
			node = ExpressionNodeFactory.create(ctx.assignmentName.getText());
		} else if(ctx.literal() != null) {
			node = new StringNode(new StringLiteral(ctx.literal().getText()).toString());
		} 
		return new AssignClauseNode(node);
	}
}
