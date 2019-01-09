package de.cobolj.statements.subtract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.statement.add.MathImplNode;

/**
 * Führt das SUBTRACT ... FROM - Statement aus. Wesentlicher Teil der Implementierung ist für 
 * die Grundrechenarten identisch und ausgelagert.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "SubtractFromStatement")
public class SubtractFromStatementNode extends MathImplNode {
	/**
	 * @see MathImplNode
	 */
	public SubtractFromStatementNode(List<ExpressionNode> summands, List<FrameSlot> results, List<Boolean> rounded) {
		super(summands, results, rounded);
	}

	/**
	 * Left wird aufadiert und auf jeden Wert von rightResult subtrahiert.
	 * 
	 * @param left Liste der linken Operanden. Die Summe dieser Operanden wird verwendet
	 * @param right keine Verwendung
	 * @param rightResult Jeder Wert dieser Liste wird um die Summe von Left erniedrigt.
	 * @return rightResult erniedrigt um die Summe von left
	 */
	@Override
	protected List<BigDecimal> calculateNewValue(List<BigDecimal> left, BigDecimal right,
			List<BigDecimal> rightResult) {
		List<BigDecimal> resultList = new ArrayList<>();
		BigDecimal leftOperand = BigDecimal.ZERO;
		for(BigDecimal leftNode : left) {
			leftOperand = leftOperand.add(leftNode);
		}
		
		for(BigDecimal result : rightResult) {
			resultList.add(result.subtract(leftOperand));
		}
		
		return resultList;
	}

}
