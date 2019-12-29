package de.cobolj.nodes;

import static de.cobolj.parser.ParserHelper.accept;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.division.data.PictureCarinalityVisitor;
import de.cobolj.parser.division.data.PictureCharsVisitor;

/**
 * 
 * pictureString: (pictureChars pictureCardinality?)
 * 
 * Als Ergebnis normiert dieser Visitor die Darstellung des Picture-Strings. So
 * wird beispielsweise aus 9(2)V9(2) zu 99.99
 * 
 * @author flaechsig
 *
 */
public class PictureStringVisitor extends Cobol85BaseVisitor<String> {

	@Override
	public String visitPictureString(Cobol85Parser.PictureStringContext ctx) {
		String fullString = "";
		for (int i = 0; i < ctx.pictureChars().size(); i++) {
			String pictureChar = accept(ctx.pictureChars(i), (new PictureCharsVisitor()));
			if (ctx.pictureCardinality(i) != null) {
				fullString += accept(ctx.pictureCardinality(i), new PictureCarinalityVisitor(pictureChar));
			} else {
				fullString += pictureChar;
			}
		}
		return fullString;
	}
}
