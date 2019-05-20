package de.cobolj.parser.division.identification;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.division.identification.ProgramIdParagraphNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.ProgramIdParagraphContext;
import de.cobolj.parser.CobolWordVisitor;

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
		notImplemented(ctx.COMMON());
		notImplemented(ctx.INITIAL());
		notImplemented(ctx.LIBRARY());
		notImplemented(ctx.DEFINITION());
		notImplemented(ctx.RECURSIVE());
		notImplemented(ctx.PROGRAM());
		notImplemented(ctx.commentEntry());
		
		String programName = accept(ctx.progName, CobolWordVisitor.INSTANCE);
		
		return new ProgramIdParagraphNode(programName);
	}
}
