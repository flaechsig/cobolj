package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;

/**
 * 
 * qualifiedDataNameFormat1:  (dataName | conditionName)(qualifiedInData+ inFile? | inFile)?
 * 
 * @author flaechsig
 *
 */
public class QualifiedDataNameFormat1Visitor extends Cobol85BaseVisitor<PictureNode> {

	public static final QualifiedDataNameFormat1Visitor INSTANCE = new QualifiedDataNameFormat1Visitor();
	
	private QualifiedDataNameFormat1Visitor() {	}

	@Override
	public PictureNode visitQualifiedDataNameFormat1(Cobol85Parser.QualifiedDataNameFormat1Context ctx) {
		notImplemented(ctx.conditionName());
		notImplemented(ctx.qualifiedInData());
		notImplemented(ctx.inFile());
		
		PictureNode result = null;
		result = accept(ctx.dataName(), DataNameVisitor.INSTANCE);
		
		return result;
	}
}
