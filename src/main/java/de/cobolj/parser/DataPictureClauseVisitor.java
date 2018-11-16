package de.cobolj.parser;

import de.cobolj.nodes.PictureFactory;
import de.cobolj.nodes.PictureNode;
import de.cobolj.nodes.PictureStringVisitor;

/**
 * 
 * dataPictureClause: (PICTURE | PIC) IS? pictureString+
 * 
 * @author flaechsig
 *
 */
public class DataPictureClauseVisitor extends Cobol85BaseVisitor<PictureNode> {

	@Override
	public PictureNode visitDataPictureClause(Cobol85Parser.DataPictureClauseContext ctx) {
		String picString = ctx.pictureString().accept(new PictureStringVisitor());
						
		return new PictureNode(PictureFactory.create(picString)); 
	}

}
