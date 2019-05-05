package de.cobolj.parser;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import org.antlr.v4.runtime.RuleContext;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.StringNode;
import de.cobolj.parser.division.data.QualifiedDataNameVisitor;

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
		notImplemented(ctx.tableCall());
		notImplemented(ctx.functionCall());
		notImplemented(ctx.specialRegister());

		String result = accept(ctx.qualifiedDataName(), QualifiedDataNameVisitor.INSTANCE);
		
		return result;
	}
}
