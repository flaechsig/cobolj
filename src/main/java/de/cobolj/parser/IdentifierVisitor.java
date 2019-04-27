package de.cobolj.parser;

import org.antlr.v4.runtime.RuleContext;

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
		ParserHelper.notImplemented(ctx.tableCall());
		ParserHelper.notImplemented(ctx.functionCall());
		ParserHelper.notImplemented(ctx.specialRegister());

		String result = null;
		if(ctx.qualifiedDataName()!=null) {
			result = ctx.qualifiedDataName().accept(QualifiedDataNameVisitor.INSTANCE);
		}
		return result;
	}
}
