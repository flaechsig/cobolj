package de.cobolj.nodes;

import java.math.BigDecimal;

import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Implementiert eines Ausdrucks auf Zahlenbereichen (negativ, null, positiv)
 * 
 * @author flaechsig
 *
 */
public class RelationSignConditionNode extends ConditionNode {
	/** Kennzeichen, ob ein Positiv-Check (IS) bzw. ein Negativ-Check (IS-NOT) durchgeführt werden soll */
	private final boolean checkPositiv;
	/** Kennzeichen der Bereichsprüfung -1=negativ, 0=null, +1=positiv */
	private final int checkCompare;
	/** Ausdruck der überprüft wird */
	@Child
	ExpressionNode aritmetic;

	public RelationSignConditionNode(ExpressionNode arithmetic, boolean checkPositive, int checkCompare) {
		this.aritmetic = arithmetic;
		this.checkPositiv = checkPositive;
		this.checkCompare = checkCompare;
	}

	@Override
	public Boolean executeGeneric(VirtualFrame frame) {
		BigDecimal checkValue = (BigDecimal) aritmetic.executeGeneric(frame);
		boolean compareResult = checkValue.compareTo(BigDecimal.ZERO) == this.checkCompare;
		
		return compareResult == checkPositiv;
	}

}
