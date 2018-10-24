package de.cobolj.nodes;

import java.util.List;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.CobolCallableNode;

/**
 * Führt eine Liste von Paragraphen oder Sections aus, die im Rahmen einer
 * Perform-Anweisung zur Ausführung angegeben werden können.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "PerformProcedure")
public class PerformProcedureStatementNode extends PerformStatementNode {

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
		List<RootCallTarget> targets = CobolCallableNode.findFromTo(startFunction, endFunction);
		for (RootCallTarget target : targets) {
			target.call();
		}
		return null;
	}

}
