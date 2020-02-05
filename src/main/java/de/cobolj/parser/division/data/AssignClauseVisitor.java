package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.division.data.AssignClauseNode;
import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.AssignClauseContext;

/**
 * assignClause : ASSIGN TO? ( DISK | DISPLAY | KEYBOARD | PORT | PRINTER |
 * READER | REMOTE | TAPE | VIRTUAL | assignmentName=identifier | literal )
 * 
 * @author flaechsig
 *
 */
public class AssignClauseVisitor extends Cobol85BaseVisitor<AssignClauseNode> {
	
	@Override
	public AssignClauseNode visitAssignClause(AssignClauseContext ctx) {
		notImplemented(ctx.DISK());
		notImplemented(ctx.DISPLAY());
		notImplemented(ctx.KEYBOARD());
		notImplemented(ctx.PORT());
		notImplemented(ctx.PRINTER());
		notImplemented(ctx.READER());
		notImplemented(ctx.REMOTE());
		notImplemented(ctx.TAPE());
		notImplemented(ctx.VIRTUAL());
		
		ExpressionNode node= null;
		node = accept(node, ctx.assignmentName, new AssignmentNameVisitor() );
		node = accept(node, ctx.literal(), LiteralVisitor.INSTANCE);

		return new AssignClauseNode(node);
	}}
