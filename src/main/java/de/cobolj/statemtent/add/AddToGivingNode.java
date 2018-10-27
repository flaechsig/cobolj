package de.cobolj.statemtent.add;

import java.math.BigDecimal;
import java.util.List;

import com.oracle.truffle.api.frame.FrameSlot;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.parser.statement.add.AddToStatementNode;

public class AddToGivingNode extends AddToStatementNode {

	public AddToGivingNode(List<ExpressionNode> summands, List<FrameSlot> slots, List<Boolean> roundeds) {
		super(summands, slots, roundeds);
	}
	

	/**
	 * Bei GIVEN wir das Ergebnis mit der Summe überschrieben (nicht addiert)
	 *
	 */
	protected BigDecimal calculateNewValue(BigDecimal sum, BigDecimal value) {
		return sum;
	}
}

