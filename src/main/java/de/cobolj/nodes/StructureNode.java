package de.cobolj.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Oberklasse für alle strukturgebenden Konstrukte. Z.b. Section, Paragraph, Sentence
 * @author flaechsig
 *
 */
public abstract class StructureNode extends CobolNode {

	public final static Map<String, StructureNode> PARAGRAPH_REGISTRY = new HashMap<>();
	

	public static List<StructureNode> findFromTo(String startFunction, String endFunction) {
		List<StructureNode> result = new ArrayList<>();
		boolean firstfound = false;
		for (Entry<String, StructureNode> entry : PARAGRAPH_REGISTRY.entrySet()) {
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
