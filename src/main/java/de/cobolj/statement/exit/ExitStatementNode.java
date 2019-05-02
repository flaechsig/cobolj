package de.cobolj.statement.exit;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.statement.StatementNode;

public class ExitStatementNode extends StatementNode {

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		System.out.println("ExitStatementNode nicht implementiert");
		return "ExitStatementNode nicht implementiert";
	}

}
