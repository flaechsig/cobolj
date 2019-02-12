package de.cobolj.parser;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.statement.StatementNode;

@NodeInfo(shortName="CloseStatement")
public class CloseStatementNode extends StatementNode {

	@Children
	private final CloseFileNode[] fileNode;

	public CloseStatementNode(List<CloseFileNode> fileNodes) {
		this.fileNode = fileNodes.toArray(new CloseFileNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for(CloseFileNode node : fileNode) {
			last = node.executeGeneric(frame);
		}
		return last;
	}

}
