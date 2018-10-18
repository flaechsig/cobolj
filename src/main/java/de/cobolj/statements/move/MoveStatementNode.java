package de.cobolj.statements.move;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.statements.StatementNode;

/**
 * Implementierung des MOVE-Statements.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="MOVE")
public class MoveStatementNode extends StatementNode {
	@Child
	StatementNode moveStatement;
	
	public MoveStatementNode(StatementNode stmt) {
		this.moveStatement = stmt;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return moveStatement;
	}

}