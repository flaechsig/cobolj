package de.cobolj.parser;

import org.antlr.v4.runtime.ParserRuleContext;

import de.cobolj.nodes.CobolNode;

public class ParserUtil {
	private ParserUtil() {}
	
	/**
	 * Führt auf dem Context eine Null-Prüfung durch und wenn dieser nicht NULL ist, dann wird der darauf 
	 * ausgeführt 
	 * @param ctx Context oder NULL
	 * @param visitor Auszuführender Visitor
	 * @returnCobolNode oder Spezieller. Null, wenn der Context null war.
	 */
	public static CobolNode accept(ParserRuleContext ctx, Cobol85BaseVisitor visitor)  {
		if(ctx != null) {
			return (CobolNode) ctx.accept(visitor);
		}
		return null;
	}
	
	/**
	 * Liefert true, wenn ctx ungleich null ist;
	 * @param ctx
	 */
	public static boolean check(Object ctx) {
		return ctx != null;
	}
}
