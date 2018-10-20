package de.cobolj.parser;

import de.cobolj.nodes.PictureNode;
import de.cobolj.nodes.PictureStringVisitor;

/**
 * 
 * dataPictureClause: (PICTURE | PIC) IS? pictureString
 * 
 * @author flaechsig
 *
 */
public class DataPictureClauseVisitor extends Cobol85BaseVisitor<PictureNode> {

	@Override
	public PictureNode visitDataPictureClause(Cobol85Parser.DataPictureClauseContext ctx) {
		PictureStringVisitor visitor = new PictureStringVisitor();
		return ctx.pictureString().accept(visitor); 
	}

}
