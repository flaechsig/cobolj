package de.cobolj.parser;

/**
 * 
 * pictureCardinality: LPARENCHAR integerLiteral RPARENCHAR
 * 
 * @author flaechsig
 *
 */
public class PictureCarinalityVisitor extends Cobol85BaseVisitor<Integer> {

	@Override
	public Integer visitPictureCardinality(Cobol85Parser.PictureCardinalityContext ctx) {
		return Integer.valueOf(ctx.integerLiteral().getText());
	}
}
