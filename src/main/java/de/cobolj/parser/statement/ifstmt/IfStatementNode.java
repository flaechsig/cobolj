package de.cobolj.parser.statement.ifstmt;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.BlockNode;
import de.cobolj.nodes.ConditionNode;
import de.cobolj.statement.StatementNode;

/**
 * Implementierung des IF Statements.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="If")
public class IfStatementNode extends StatementNode {
	/** Bedingung für das IF Statement  */
	@Child
	private ConditionNode condition;
	/** Block der ausgeführt wird, wenn die Condition wahr ist */
	@Child
	private BlockNode ifThen;
	/** Optionaler Block der ausgeführt wird, wenn die Condition nicht wahr ist */
	@Child
	private BlockNode ifElse;

	public IfStatementNode(ConditionNode condition, BlockNode ifThen, BlockNode ifElse) {
		this.condition = condition;
		this.ifThen = ifThen;
		this.ifElse = ifElse;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		if(condition.executeGeneric(frame)) {
			ifThen.executeGeneric(frame);
		} else {
			if(ifElse != null) {
				ifElse.executeGeneric(frame);
			}
		}
		return null;
	}

}
