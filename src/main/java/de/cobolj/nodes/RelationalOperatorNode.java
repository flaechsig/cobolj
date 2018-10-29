package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="RelationalOperator")
public class RelationalOperatorNode extends ConditionNode {
	public enum Operator {
		GREATER, LESS, EQUAL, GREATEREQUAL, LESSEQUAL
	}

	@Child
	private ArithmeticNode left;
	@Child
	private ArithmeticNode right;
	private final Operator operator;
	private final boolean negate;
	

	public RelationalOperatorNode(ArithmeticNode left, ArithmeticNode right, Operator operator, boolean negate) {
		this.left = left;
		this.right = right;
		this.operator = operator;
		this.negate = negate;
	}

	@Override
	public Boolean executeGeneric(VirtualFrame frame) {
		boolean result=false;
		Number leftN = left.executeGeneric(frame);
		Number rightN = right.executeGeneric(frame);
		double compare = leftN.doubleValue() - rightN.doubleValue();
		switch(operator) {
		case EQUAL:
			result = compare==0;
			break;
		case GREATER:
			result = compare>0;
			break;
		case GREATEREQUAL:
			result = compare>=0;
			break;
		case LESS:
			result = compare<0;
			break;
		case LESSEQUAL:
			result = compare<=0;
			break;
		}
		return negate!=result;
	}
}
