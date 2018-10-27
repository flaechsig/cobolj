package de.cobolj.statements.perform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;

/**
 * Implementiert das Statement PERFORM XXXX TIMES ... END PERFORM.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "PerformTimes")
public class PerformUntilNode extends PerformTypeNode {
	/**
	 * Kennzeichen, ob die Schleifenbedingung vor dem Schleifen-Body geprüft werden
	 * soll
	 */
	protected final boolean testBefore;
	/** Schleifenzähler */
	@Child
	protected ExpressionNode until;

	public PerformUntilNode(boolean testBefore, ExpressionNode until, PerformStatementNode perform) {
		super(perform);
		this.until = until;
		this.testBefore = testBefore;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {

		while (true) {
			boolean conditionResult = (boolean) until.executeGeneric(frame);
			if (testBefore && conditionResult) {
				break;
			}
			perform.executeGeneric(frame);
			if (!testBefore && conditionResult) {
				break;
			}
		}
		return null;
	}

}
