package de.cobolj.nodes;

import java.math.BigDecimal;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Implementierung der Multiplikation.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="MultNode")
public class AddNode extends ArithmeticNode {
	@Child
	private ArithmeticNode left;
	@Child
	private ArithmeticNode right;

	public AddNode(ArithmeticNode leftPowers, ArithmeticNode rightPowers) {
		this.left = leftPowers;
		this.right = rightPowers;
	}

	@Override
	public Number executeGeneric(VirtualFrame frame) {
		BigDecimal left = (BigDecimal) this.left.executeGeneric(frame);
		BigDecimal right = (BigDecimal) this.right.executeGeneric(frame);
		return left.add(right);
	}

}
