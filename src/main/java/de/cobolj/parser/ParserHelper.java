package de.cobolj.parser;

import java.util.List;

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


}
