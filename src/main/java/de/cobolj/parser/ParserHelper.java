package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

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
		if (obj != null) {
			throw new RuntimeException("Not Implemented");
		}
	}

	/**
	 * @see ParserHelper#notImplemented(Object)
	 */
	public static void notImplemented(List<?> obj) {
		if (!obj.isEmpty()) {
			throw new RuntimeException("Not Implemented");
		}
	}

	/**
	 * @see ParserHelper#notImplemented(Object)
	 */
	public static void notImplemented() {
		throw new RuntimeException("Not Implemented");
	}

	/**
	 * @see ParserHelper#notImplemented(Object)
	 */
	public static void notImplemented(boolean condition) {
		if (condition) {
			throw new RuntimeException("Not Implemented");
		}
	}

	/**
	 * Liefert true, wenn ctx ungleich null ist;
	 * 
	 * @param ctx
	 */
	public static boolean check(Object ctx) {
		return ctx != null;
	}

	/**
	 * Kappselt den Standard-Code für den Aufruf von Child-Visitor-Elementen
	 * 
	 * @param ctx     Context-Child-Element
	 * @param visitor Aufzurufender Visitor
	 * @return null, wenn ctx <code>null</code> ist. Ansonsten den Wert des Visitors
	 */
	public static <T> T accept(ParserRuleContext ctx, Cobol85BaseVisitor<? extends T> visitor) {
		if (ctx == null) {
			return null;
		} else {
			return ctx.accept(visitor);
		}
	}

	/**
	 * @see #accept(ParserRuleContext, Cobol85BaseVisitor)
	 * 
	 *      Zusätzlich wird eine Condition übergeben, die null sein muss, damit der
	 *      Visitor aufgerufen wird. Andernfalls wird die Condition zurückgeliefert.
	 * 
	 */
	public static <T> T accept(T condition, ParserRuleContext ctx, Cobol85BaseVisitor<? extends T> visitor) {
		if (condition != null) {
			return condition;
		}
		return accept(ctx, visitor);
	}

	/**
	 * @see #accept(ParserRuleContext, Cobol85BaseVisitor)
	 */
	public static <T> List<T> accept(List<? extends ParserRuleContext> ctx, Cobol85BaseVisitor<? extends T> visitor) {
		if (ctx == null) {
			return null;
		} else {
			return ctx.stream().map(r -> r.accept(visitor)).collect(Collectors.toList());
		}
	}

	/**
	 * @see #accept(ParserRuleContext, Cobol85BaseVisitor)
	 */
	public static boolean accept(TerminalNode ctx) {
		return ctx != null ? true : false;
	}
}
