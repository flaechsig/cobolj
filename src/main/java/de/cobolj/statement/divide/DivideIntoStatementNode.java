package de.cobolj.statements.divide;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.statement.add.MathImplNode;

/**. 
 * Implementiert DIVIDE literalOrIdentifier INTO resultIdentifier+
 * 
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "DivideIntoStatmeent")
public class DivideIntoStatementNode extends MathImplNode {
	/**
	 * @see MathImplNode
	 */
	public DivideIntoStatementNode(List<ExpressionNode> summands, List<FrameSlot> results, List<Boolean> rounded) {
		super(summands, results, rounded);
	}
	
	/**
	 * Fürht die Division auf die Teile hinter INTO durch
	 * 
	 * @param left linker Divsor (in eine Liste eingepackt)
	 * @param right keine Verwendung
	 * @param rightResult Dividenden, auf die der Divisor angewendet wird
	 * @return rightResult Enhält die Qutionten von left und rightResult
	 */
	@Override
	protected List<BigDecimal> calculateNewValue(List<BigDecimal> left, BigDecimal right, List<BigDecimal> rightResult) {
		List<BigDecimal> resultList = new ArrayList<>();
		BigDecimal leftOperand = left.get(0);
		
		for(BigDecimal result : rightResult) {
			resultList.add(result.divide(leftOperand));
		}
		
		return resultList;
	}

}
