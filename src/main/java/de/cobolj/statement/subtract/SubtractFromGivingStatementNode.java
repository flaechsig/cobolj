package de.cobolj.statement.subtract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.statement.add.MathImplNode;

/**
 * Führt MULTIPLY multiplicant BY multiplicator GIVING result
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "SubtractFromStatement")
public class SubtractFromGivingStatementNode extends MathImplNode {
	/**
	 * @see MathImplNode
	 */
	public SubtractFromGivingStatementNode(List<ExpressionNode> summands, ExpressionNode mid, List<String> results, List<Boolean> rounded) {
		super(summands, mid, results, rounded);
	}

	/**
	 * Drei Parameter-Variante des Multiply-Befehls. Für jede Variable in rightResults gilt rightResult = multiplikant*multiplikator
	 * In Cobol MULTIPLY literalOrIdentifier BY literalOrIdentifier GIVING identifier+
	 * 
	 * @param left Liste mit nur einem Wert - dem Multiplikant
	 * @param right rechter Operand, der in das Ergebnis Einfließt
	 * @param rightResult unbenutzt bzw. nur für die Anzahl der zu erzeugenden Ergebnis-Elemente
	 * @result Ergebnis-Array der Länge length(rightResult) mit sum(left) + right.
	 * 
	 */
	@Override
	protected List<BigDecimal> calculateNewValue(List<BigDecimal> left, BigDecimal right,
			List<BigDecimal> rightResult) {
		List<BigDecimal> resultList = new ArrayList<>();
		BigDecimal leftOperand = BigDecimal.ZERO;
		for(BigDecimal leftNode : left) {
			leftOperand = leftOperand.add(leftNode); // Bei der Muliplikation hat die Liste nur einen Wert...
		}

		for(int i=0; i<rightResult.size(); i++) {
			resultList.add(right.subtract(leftOperand));
		}
		return resultList;
	}

}
