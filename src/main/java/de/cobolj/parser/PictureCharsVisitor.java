package de.cobolj.parser;

public class PictureCharsVisitor extends Cobol85BaseVisitor<String> {

	@Override
	public String visitPictureChars(Cobol85Parser.PictureCharsContext ctx) {
		return ctx.getChild(0).getText();
	}
}
