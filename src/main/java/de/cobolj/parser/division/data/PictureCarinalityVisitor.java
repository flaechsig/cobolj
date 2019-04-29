package de.cobolj.parser.division.data;

import org.apache.commons.lang3.StringUtils;

import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.PictureCardinalityContext;

/**
 * 
 * pictureCardinality: LPARENCHAR integerLiteral RPARENCHAR
 * 
 * Mit diesem Visitor wird aus der Kurzschreibweise die Langschreibweise.
 * Dadurch wird die Verarbeitung vereinheitlich.
 * 
 * @author flaechsig
 *
 */
public class PictureCarinalityVisitor extends Cobol85BaseVisitor<String> {

	private String pictureChar;

	public PictureCarinalityVisitor(String pictureChar) {
		this.pictureChar = pictureChar;
	}

	@Override
	public String visitPictureCardinality(Cobol85Parser.PictureCardinalityContext ctx) {
		String lastCharacter = pictureChar.substring(pictureChar.length()-1);
		return StringUtils.rightPad(pictureChar, Integer.valueOf(ctx.INTEGERLITERAL().getText())+pictureChar.length()-1, lastCharacter);
	}
}
