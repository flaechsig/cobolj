package de.cobolj.parser;

import org.antlr.v4.runtime.ParserRuleContext;

import de.cobolj.nodes.CobolNode;

public class ParserUtil {
	private ParserUtil() {}
	
	/**
	 * Liefert true, wenn ctx ungleich null ist;
	 * @param ctx
	 */
	public static boolean check(Object ctx) {
		return ctx != null;
	}
}
