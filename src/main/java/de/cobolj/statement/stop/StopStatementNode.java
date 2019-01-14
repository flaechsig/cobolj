package de.cobolj.statement.stop;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.statement.StatementNode;

/**
 * Implementiert das Statement STOP RUN.
 * 
 * @author flaechsig
 *
 */
public class StopStatementNode extends StatementNode {

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		throw StopStatementException.SINGLETON;
	}

}
