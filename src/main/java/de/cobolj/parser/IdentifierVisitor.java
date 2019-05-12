package de.cobolj.parser;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.division.data.QualifiedDataNameVisitor;
import de.cobolj.parser.division.data.TableCallVisitor;

/**
 * 
 * identifier: qualifiedDataName | tableCall | functionCall | specialRegister
 *  
 * @author flaechsig
 *
 */
public class IdentifierVisitor extends Cobol85BaseVisitor<PictureNode> {
	public static IdentifierVisitor INSTANCE = new IdentifierVisitor();
	
	private IdentifierVisitor() {}

	@Override
	public PictureNode visitIdentifier(Cobol85Parser.IdentifierContext ctx) {
		notImplemented(ctx.functionCall());
		notImplemented(ctx.specialRegister());

		PictureNode result = accept(ctx.qualifiedDataName(), QualifiedDataNameVisitor.INSTANCE);
		result = accept(result, ctx.tableCall(), new TableCallVisitor());
		
		return result;
	}
}
