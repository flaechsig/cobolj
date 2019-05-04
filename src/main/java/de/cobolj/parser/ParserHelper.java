package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ParserRuleContext;

import de.cobolj.parser.Cobol85Parser.IdentifierContext;

/**
 * Hilfsklasse für das Parsing.
 * 
 * @author flaechsig
 *
 */
public final class ParserHelper {
	private ParserHelper() {
	}

	/**
	 * Prüft, ob der übergene Parameter nicht null ist. In diesem Fall wird eine
	 * RuntimeException geworfen, da ein Feature angegeben wurde, dass durch den
	 * Parser noch nicht unterstatützt wird.
	 * 
	 * @param obj Zu prüfendes Objekt
	 */
	public static void notImplemented(Object obj) {
		if(obj != null) {
			throw new RuntimeException("Not Implemented");
		}
	}
	
	/**
	 * @see ParserHelper#notImplemented(Object)
	 */
	public static void notImplemented(List<?> obj) {
		if(!obj.isEmpty()) {
			throw new RuntimeException("Not Implemented");
		}
	}

	public static void notImplemented() {
		throw new RuntimeException("Not Implemented");
	}


	/**
	 * Liefert true, wenn ctx ungleich null ist;
	 * @param ctx
	 */
	public static boolean check(Object ctx) {
		return ctx != null;
	}
	
	/**
	 * Kappselt den Standard-Code für den Aufruf von Child-Visitor-Elementen
	 * 
	 * @param ctx Context-Child-Element
	 * @param visitor Aufzurufender Visitor
	 * @return null, wenn ctx <code>null</code> ist. Ansonsten den Wert des Visitors
	 */
	public static <T>  T accept( ParserRuleContext ctx, Cobol85BaseVisitor<? extends T> visitor) {
		if(ctx == null) {
			return null;
		} else {
			return ctx.accept(visitor);
		}
	}

	public static <T> List<T> accept(List<? extends ParserRuleContext> ctx, Cobol85BaseVisitor<? extends T>  visitor) {
		if(ctx == null) {
			return null;
		} else {
			return ctx.stream().map(result -> result.accept(visitor)).collect(Collectors.toList());
		}
	}
}
