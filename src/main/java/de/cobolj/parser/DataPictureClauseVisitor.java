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
	/** Name des Picture */
	private String name;

	public DataPictureClauseVisitor(String name) {
		this.name = name;
	}

	@Override
	public PictureNode visitDataPictureClause(Cobol85Parser.DataPictureClauseContext ctx) {
		String picString = ctx.pictureString().accept(new PictureStringVisitor());
						
		return new PictureNode(PictureFactory.create(name, picString)); 
	}

}
