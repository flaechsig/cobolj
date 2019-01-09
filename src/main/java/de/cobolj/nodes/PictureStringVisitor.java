package de.cobolj.nodes;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.PictureCarinalityVisitor;
import de.cobolj.parser.PictureCharsVisitor;

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
		for(int i=0; i<ctx.pictureChars().size();i++) {
			String pictureChar = ctx.pictureChars(i).accept(new PictureCharsVisitor());
			fullString += ctx.pictureCardinality(i).accept(new PictureCarinalityVisitor(pictureChar));
		}
		return fullString;
	}
}
