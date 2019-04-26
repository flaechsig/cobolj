package de.cobolj.statement.multiply;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.statement.add.MathImplNode;

/**. 
 * Implementiert MULTIPLY literalOrIdentifier BY identifier+
 * 
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "MultiplyByStatement")
public class MultiplyByStatementNode extends MathImplNode {
	/**
	 * @see MathImplNode
	 */
	public MultiplyByStatementNode(List<ExpressionNode> summands, List<String> results, List<Boolean> rounded) {
		super(summands, results, rounded);
	}
	
	/**
	 * Left wird aufadiert und auf jeden Wert von rightResult addiert.
	 * 
	 * @param left linker Multiplikant (in eine Liste eingepackt)
	 * @param right keine Verwendung
	 * @param rightResult Jeder Wert dieser Liste wird mit left multipliziert.
	 * @return rightResult Enhält das Produkt von left mit dem Eintrag auf rightResult
	 */
	@Override
	protected List<BigDecimal> calculateNewValue(List<BigDecimal> left, BigDecimal right, List<BigDecimal> rightResult) {
		List<BigDecimal> resultList = new ArrayList<>();
		BigDecimal leftOperand = left.get(0);
		
		for(BigDecimal result : rightResult) {
			resultList.add(result.multiply(leftOperand));
		}
		
		return resultList;
	}

}
