package de.cobolj.statement.accept;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.statement.ChangeElementaryItemNode;
import de.cobolj.statement.StatementNode;

/**
 * Implementierung des Accept-Statements.
 * 
 * Aus einer Input-Quelle wird der Wert für das Accept-Statement ausgelesen und zurück geliefert.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="Acccept")
public class AcceptStatementNode extends StatementNode {

	@Child
	private ChangeElementaryItemNode change; 
	
	public AcceptStatementNode(ChangeElementaryItemNode change) {
		this.change = change;
	}
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return change.executeGeneric(frame);
	}

}
