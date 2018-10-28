package de.cobolj;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

import de.cobolj.nodes.CobolNode;
import de.cobolj.nodes.SentenceNode;
import de.cobolj.parser.statement.nextsentence.NextSentenceExcetion;

public class CobolCallableNode extends RootNode {
	private static final Map<String, RootCallTarget> FUNCTION_REGISTRY = new LinkedHashMap<>();

	/** Name des Paragraphen */
	private final String paragraph;
	@Children
	private final CobolNode[] body;

	public CobolCallableNode(TruffleLanguage<?> language, String paragraph, List<? extends CobolNode> body) {
		super(language);
		this.paragraph = paragraph.toUpperCase();
		this.body = body.toArray(new CobolNode[] {});
		FUNCTION_REGISTRY.put(this.paragraph, Truffle.getRuntime().createCallTarget(this));
	}

	@Override
	public Object execute(VirtualFrame frame) {
		Object last = null;
		for (CobolNode node : body) {
			try {
				last = node.executeGeneric(frame);
			} catch (NextSentenceExcetion e) {
				if (node instanceof SentenceNode) {
					continue;
				} else {
					throw e;
				}
			}
		}
		return last;
	}

	/**
	 * @param name Name des zu suchenden Paragraphen/Section
	 * @return Funktion, die sich hinter dem Namen verbirgt
	 */
	public static RootCallTarget findByName(String name) {
		return FUNCTION_REGISTRY.get(name.toUpperCase());
	}

	/**
	 * Ermittel die Liste von Funktion, die durch den Berech startFunction und
	 * endFunction beschrieben wird.
	 * 
	 * @param startFunction Erste Funktion in der Liste aller Funktionen, die diesen
	 *                      Namen trägt
	 * @param endFunction   Letzter Funktion in der Liste aller Funktionen, die
	 *                      diesen Namen trägt
	 * @return Liste aller Funktionen die durch den Suchbereich (Start, Ende)
	 *         beschrieben wurde.
	 * 
	 */
	public static List<RootCallTarget> findFromTo(String startFunction, String endFunction) {
		List<RootCallTarget> result = new ArrayList<>();
		boolean firstfound = false;
		for (Entry<String, RootCallTarget> entry : FUNCTION_REGISTRY.entrySet()) {
			if (!firstfound) {
				firstfound = startFunction.equals(entry.getKey());
			}
			// Wenn das erste Element gefunden wurde, dann die Liste füllen
			if (firstfound) {
				result.add(entry.getValue());
			}
			// Wenn das aktuelle Element dem letzten Element enspricht, dann nicht weiter
			// einsammeln
			if (endFunction.equals(entry.getKey())) {
				break;
			}
		}
		return result;
	}
}
