package de.cobolj.statement.add;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.statement.add.MathImplNode;

/**. 
 * Implementiert ADD literalOrIdentifier+ TO identifier+
 * 
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "AddToStatement")
public class AddToStatementNode extends MathImplNode {
	/**
	 * @see MathImplNode
	 */
	public AddToStatementNode(List<ExpressionNode> summands, List<String> results, List<Boolean> rounded) {
		super(summands, results, rounded);
	}
	
	/**
	 * Left wird aufadiert und auf jeden Wert von rightResult addiert.
	 * 
	 * @param left Liste der linken Operanden. Die Summe dieser Operanden wird verwendet
	 * @param right keine Verwendung
	 * @param rightResult Jeder Wert dieser Liste wird um die Summe von Left erhöht.
	 * @return rightResult erhöht um die Summe von left
	 */
	@Override
	protected List<BigDecimal> calculateNewValue(List<BigDecimal> left, BigDecimal right, List<BigDecimal> rightResult) {
		List<BigDecimal> resultList = new ArrayList<>();
		BigDecimal leftOperand = BigDecimal.ZERO;
		for(BigDecimal leftNode : left) {
			leftOperand = leftOperand.add(leftNode);
		}
		
		for(BigDecimal result : rightResult) {
			resultList.add(result.add(leftOperand));
		}
		
		return resultList;
	}

}
