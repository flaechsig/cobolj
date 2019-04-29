package de.cobolj.parser.division.data;

import de.cobolj.nodes.PictureFactory;
import de.cobolj.nodes.PictureStringVisitor;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.DataPictureClauseContext;
import de.cobolj.runtime.Picture;
import de.cobolj.runtime.PictureGroup;

/**
 * 
 * dataPictureClause: (PICTURE | PIC) IS? pictureString+
 * 
 * @author flaechsig
 *
 */
public class DataPictureClauseVisitor extends Cobol85BaseVisitor<Picture> {
	/** Name des Picture */
	private final String name;
	private final PictureGroup parent;

	public DataPictureClauseVisitor(String name, PictureGroup parent) {
		this.name = name;
		this.parent = parent;
	}

	@Override
	public Picture visitDataPictureClause(Cobol85Parser.DataPictureClauseContext ctx) {
		String picString = ctx.pictureString().accept(new PictureStringVisitor());
						
		return PictureFactory.create(name, picString, parent); 
	}

}
