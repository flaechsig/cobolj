package de.cobolj.nodes;

import java.math.BigDecimal;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Implementierung der Division.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="MultNode")
public class DivNode extends ExpressionNode {
	@Child
	private ExpressionNode left;
	@Child
	private ExpressionNode right;

	public DivNode(ExpressionNode leftPowers, ExpressionNode rightPowers) {
		this.left = leftPowers;
		this.right = rightPowers;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		BigDecimal left = (BigDecimal) this.left.executeGeneric(frame);
		BigDecimal right = (BigDecimal) this.right.executeGeneric(frame);
		return new BigDecimalNode(left.divide(right));
	}

}
