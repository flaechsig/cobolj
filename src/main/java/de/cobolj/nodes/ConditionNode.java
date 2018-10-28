package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Oberklasse der Condition-Klassen. Wesentliches Merkmal dieser Klassen ist,
 * das die Ausführung von executeGeneric immer ein Boolean liefert.
 * 
 * @author flaechsig
 *
 */
public abstract class ConditionNode extends ExpressionNode {

	@Override
	public abstract Boolean executeGeneric(VirtualFrame frame);
}
