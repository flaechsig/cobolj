package de.cobolj.statement.perform;

import java.util.List;

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
		List<StructureNode> paragraphs = StructureNode.findFromTo(startFunction, endFunction);
		Object last = null;
		for (StructureNode p : paragraphs) {
			last = p.executeGeneric(frame);
		}
		return last;
	}

}
