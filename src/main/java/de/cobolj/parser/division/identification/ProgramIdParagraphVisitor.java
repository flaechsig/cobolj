package de.cobolj.parser.division.identification;

import de.cobolj.division.identification.ProgramIdParagraphNode;
import de.cobolj.nodes.LiteralNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.ProgramIdParagraphContext;
import de.cobolj.parser.ParserHelper;
import de.cobolj.parser.division.data.LiteralVisitor;

/**
 * programIdParagraph : PROGRAM_ID DOT_FS programName ( IS? ( COMMON | INITIAL |
 * LIBRARY | DEFINITION | RECURSIVE ) PROGRAM? )? DOT_FS? commentEntry? ;
 * 
 * @author flaechsig
 *
 */
public class ProgramIdParagraphVisitor extends Cobol85BaseVisitor<ProgramIdParagraphNode> {
	@Override
	public ProgramIdParagraphNode visitProgramIdParagraph(ProgramIdParagraphContext ctx) {
		ParserHelper.notImplemented(ctx.COMMON());
		ParserHelper.notImplemented(ctx.INITIAL());
		ParserHelper.notImplemented(ctx.LIBRARY());
		ParserHelper.notImplemented(ctx.DEFINITION());
		ParserHelper.notImplemented(ctx.RECURSIVE());
		ParserHelper.notImplemented(ctx.PROGRAM());
		ParserHelper.notImplemented(ctx.commentEntry());
		
		LiteralNode programName = ctx.programName().accept(LiteralVisitor.INSTANCE);
		
		return new ProgramIdParagraphNode(programName);
	}
}
