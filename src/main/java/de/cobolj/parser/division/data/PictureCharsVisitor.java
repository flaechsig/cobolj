package de.cobolj.parser.division.data;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.PictureCharsContext;

/**
 * pictureChars : 'A' | 'B' | 'P' | 'X' | 'Z' | '9' | '0' | SLASHCHAR |
 * COMMACHAR | PLUSCHAR | MINUSCHAR | ASTERISKCHAR | DOLLARCHAR | 'S' | V | DOT
 * | 'CR' | 'DB' ;
 * 
 * 
 * @author flaechsig
 *
 */
public class PictureCharsVisitor extends Cobol85BaseVisitor<String> {
	@Override
	public String visitPictureChars(PictureCharsContext ctx) {
		return ctx.getText();
	}
}
