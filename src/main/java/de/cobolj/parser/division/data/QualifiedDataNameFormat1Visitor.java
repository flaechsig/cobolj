package de.cobolj.parser.division.data;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;

/**
 * 
 * qualifiedDataNameFormat1 : (dataName | conditionName) ((IN | OF) dataName)*
 * ((IN | OF) tableCall)? inFile? *
 * 
 * @author flaechsig
 *
 */
public class QualifiedDataNameFormat1Visitor extends Cobol85BaseVisitor<PictureNode> {

	public static final QualifiedDataNameFormat1Visitor INSTANCE = new QualifiedDataNameFormat1Visitor();

	private QualifiedDataNameFormat1Visitor() {
	}

	@Override
	public PictureNode visitQualifiedDataNameFormat1(Cobol85Parser.QualifiedDataNameFormat1Context ctx) {
		notImplemented(ctx.conditionName());
		notImplemented(ctx.inFile());
		notImplemented(ctx.dataName().size() > 1);

		PictureNode tableCall = accept(ctx.tableCall(), new TableCallVisitor());
		return accept(ctx.dataName(0), new DataNameVisitor(tableCall));
	}
}
