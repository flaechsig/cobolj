package de.cobolj.parser;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.division.data.QualifiedDataNameVisitor;

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
		notImplemented(ctx.tableCall());
		notImplemented(ctx.functionCall());
		notImplemented(ctx.specialRegister());

		PictureNode result = accept(ctx.qualifiedDataName(), QualifiedDataNameVisitor.INSTANCE);
		
		return result;
	}
}
