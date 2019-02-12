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
public class IdentifierVisitor extends Cobol85BaseVisitor<FrameSlot> {
	public static IdentifierVisitor INSTANCE = new IdentifierVisitor();
	
	private IdentifierVisitor() {}

	@Override
	public FrameSlot visitIdentifier(Cobol85Parser.IdentifierContext ctx) {
		// FIXME: Vervollständigen
		if(ctx.qualifiedDataName()!=null) {
			StringNode literal =  ctx.qualifiedDataName().accept(QualifiedDataNameVisitor.INSTANCE);
			return StartRuleVisitor.descriptor.findOrAddFrameSlot(literal.toString().toUpperCase());
		} else {
			ParserHelper.notImplemented();
			return null;
		}
	}
}
