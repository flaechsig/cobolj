package de.cobolj.statements.accept;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.statements.ChangeElementaryItemNode;
import de.cobolj.statements.StatementNode;

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
