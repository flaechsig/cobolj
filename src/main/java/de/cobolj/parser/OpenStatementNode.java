package de.cobolj.parser;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.statement.StatementNode;

public class OpenStatementNode extends StatementNode {

	@Children
	private final OpenStatementElementNode[] openElements;

	public OpenStatementNode(List<OpenStatementElementNode> openElements) {
		this.openElements = openElements.toArray(new OpenStatementElementNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for(OpenStatementElementNode node : openElements) {
			last = node.executeGeneric(frame);
		}
		return last;
	}

}
