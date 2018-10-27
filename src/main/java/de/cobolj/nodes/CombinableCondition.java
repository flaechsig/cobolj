package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Optionale Verarbeitung eines NOT vor einem Ausdruck
 * @author flaechsig
 *
 */
public class CombinableCondition extends ExpressionNode {
	/** kennzeichen, ob auf NOT geprüft werden soll. Dann ist dieses Kennzeichen false */
	private final boolean checkPositive;
	/** Untergeordnete Condition */
	@Child
	private ExpressionNode conditionChild;
	
	public CombinableCondition(boolean checkPositive, ExpressionNode child) {
		this.checkPositive = checkPositive;
		this.conditionChild = child;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Boolean childResult = (Boolean) conditionChild.executeGeneric(frame);
		return checkPositive == childResult;
	}

}
