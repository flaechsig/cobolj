package de.cobolj.statement.perform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.StructureNode;

/**
 * Führt eine Liste von Paragraphen oder Sections aus, die im Rahmen einer
 * Perform-Anweisung zur Ausführung angegeben werden können.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "PerformProcedure")
public class PerformProcedureStatementNode extends ExpressionNode {

	/** Erster Paragraph/Section die ausgeführt werden */
	private final String startFunction;
	/**
	 * Letzter Paragraph/Section die aus der List ausgeführt werden soll. Dies kann
	 * identisch mit der Startfunction sein.
	 */
	private final String endFunction;

	public PerformProcedureStatementNode(String startFunction, String endFunction) {
		this.startFunction = startFunction.toUpperCase();
		this.endFunction = endFunction.toUpperCase();
	}

	/**
	 * Ermittelt das Call-Target zu einem gegebenen Funktionsnamen (Paragraph oder
	 * Section).
	 */
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		List<StructureNode> paragraphs = findFromTo(startFunction, endFunction);
		Object last = null;
		for (StructureNode p : paragraphs) {
			last = p.executeGeneric(frame);
		}
		return last;
	}

	private List<StructureNode> findFromTo(String startFunction, String endFunction) {
		List<StructureNode> result = new ArrayList<>();
		boolean firstfound = false;
		for (Map.Entry<String, StructureNode> entry : getContext().getParagraphRegistry().entrySet()) {
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
