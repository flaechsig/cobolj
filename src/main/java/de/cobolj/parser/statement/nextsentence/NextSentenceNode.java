package de.cobolj.parser.statement.nextsentence;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.statement.StatementNode;

public class NextSentenceNode extends StatementNode {

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		throw new NextSentenceExcetion();
	}

}
