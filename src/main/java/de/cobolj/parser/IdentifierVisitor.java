package de.cobolj.parser;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.StringNode;

/**
 * 
 * identifier: qualifiedDataName | tableCall | functionCall | specialRegister
 *  
 * @author flaechsig
 *
 */
public class IdentifierVisitor extends Cobol85BaseVisitor<String> {
	public static IdentifierVisitor INSTANCE = new IdentifierVisitor();
	
	private IdentifierVisitor() {}

	@Override
	public String visitIdentifier(Cobol85Parser.IdentifierContext ctx) {
		// FIXME: Vervollständigen
		if(ctx.qualifiedDataName()!=null) {
			String literal =  ctx.qualifiedDataName().accept(QualifiedDataNameVisitor.INSTANCE);
			return literal.toUpperCase();
		} else {
			ParserHelper.notImplemented();
			return null;
		}
	}
}
