package de.cobolj.parser.division.data;

import de.cobolj.nodes.PictureStringVisitor;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;

/**
 * 
 * dataPictureClause: (PICTURE | PIC) IS? pictureString+
 * 
 * @author flaechsig
 *
 */
public class DataPictureClauseVisitor2 extends Cobol85BaseVisitor<String> {
	@Override
	public String visitDataPictureClause(Cobol85Parser.DataPictureClauseContext ctx) {
		return ctx.pictureString().accept(new PictureStringVisitor());
	}

}
