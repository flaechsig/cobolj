package de.cobolj.statement.add;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.statement.add.MathImplNode;

/** 
 * Implementiert ADD left+ TO right GIVING result+ .
 * 
 * @author flaechsig
 *
 */
public class AddToGivingStatementNode extends MathImplNode {

	/**
	 * @see MathImplNode
	 */
	public AddToGivingStatementNode(List<ExpressionNode> summands, ExpressionNode mid, List<PictureNode> slots, List<Boolean> roundeds) {
		super(summands, mid, slots, roundeds);
	}

	/**
	 * Drei Parameter-Variante des ADD-Befehls. Für jede Variable in rightResults gilt rightResult = sum(left)+right.
	 * In Cobol ADD literalOrIdentifier+ TO literalOrIdentifier GIVING identifier+
	 * 
	 * @param left Liste mit Werten, die in das Ergebnis (durch Addition) einfließen
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
			leftOperand = leftOperand.add(leftNode);
		}

		for(int i=0; i<rightResult.size(); i++) {
			resultList.add(leftOperand.add(right));
		}
		return resultList;
	}
	

}

