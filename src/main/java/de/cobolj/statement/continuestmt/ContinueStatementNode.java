package de.cobolj.statement.continuestmt;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.statement.StatementNode;

/**
 * Implementierung des Statements CONTINUE. 
 * 
 * Dieses Statement implementiert keine Funktionalität.
 * 
 * @author flaechsig
 *
 */
public class ContinueStatementNode extends StatementNode {

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return this;
	}

}
