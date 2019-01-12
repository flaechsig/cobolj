package de.cobolj.statements.multiply;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.statement.add.MathImplNode;

/**
 * Implementiert MULTIPLY left BY right GIVING rightResult
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "MultiplyGivingStatement")
public class MultiplyGivingStatementNode extends MathImplNode {
	/**
	 * @see MathImplNode
	 */
	public MultiplyGivingStatementNode(List<ExpressionNode> summands, ExpressionNode mid, List<FrameSlot> results, List<Boolean> rounded) {
		super(summands, mid, results, rounded);
	}

	/**
	 * Drei Parameter-Variante des SUBTRACT-Befehls. Für jede Variable in rightResults gilt rightResult = right-sum(left).
	 * In Cobol SUBTRACT literalOrIdentifier+ FROM literalOrIdentifier GIVING identifier+
	 * 
	 * @param left Multiplikant (in eine Liste gepackt)
	 * @param right Multiplikator (in eine Liste gepackt)
	 * @param rightResult Ergebnis-Liste
	 * @result Produkt von left und right für jeden Eintrag in rightResult
	 * 
	 */
	@Override
	protected List<BigDecimal> calculateNewValue(List<BigDecimal> left, BigDecimal right,
			List<BigDecimal> rightResult) {
		List<BigDecimal> resultList = new ArrayList<>();
		BigDecimal leftOperand = left.get(0);

		for(int i=0; i<rightResult.size(); i++) {
			resultList.add(right.multiply(leftOperand));
		}
		return resultList;
	}

}
