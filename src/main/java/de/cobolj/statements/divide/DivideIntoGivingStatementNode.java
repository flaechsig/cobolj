package de.cobolj.statements.divide;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.statement.add.MathImplNode;

/**. 
 * Implementiert DIVIDE divisor=literalOrIdentifier INTO dividend=literalOrIdentifier GIVING resultIdentifier+
 * 
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "DivideIntoGivingStatement")
public class DivideIntoGivingStatementNode extends MathImplNode {
	/**
	 * @see MathImplNode
	 */
	public DivideIntoGivingStatementNode(List<ExpressionNode> summands, ExpressionNode right, List<FrameSlot> results, List<Boolean> rounded) {
		super(summands, right, results, rounded);
	}
	
	/**
	 * Führt die Division durch und schreibt das Ergebnis in die Ergebnismenge
	 * 
	 * @param left  Divsor (in eine Liste eingepackt)
	 * @param right Dividend
	 * @param rightResult Liste der Variablen, auf die die Operation angewendet wird. Es wird nur die Längeninforation verwendet
	 * @return Enhält die Qutionten von left und rightResult
	 */
	@Override
	protected List<BigDecimal> calculateNewValue(List<BigDecimal> left, BigDecimal right, List<BigDecimal> rightResult) {
		List<BigDecimal> resultList = new ArrayList<>();
		BigDecimal divisor = left.get(0);
		
		for(BigDecimal result : rightResult) {
			resultList.add(right.divide(divisor));
		}
		
		return resultList;
	}

}
