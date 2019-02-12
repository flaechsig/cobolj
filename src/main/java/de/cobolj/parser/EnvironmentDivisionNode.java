package de.cobolj.parser;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;

@NodeInfo(shortName="EnvironmentDivision")
public class EnvironmentDivisionNode extends CobolNode {
	/** Body der Environment-Section */
	private EnvironmentDivisionBodyNode[] body;

	public EnvironmentDivisionNode(List<EnvironmentDivisionBodyNode> body) {
		this.body = body.toArray(new EnvironmentDivisionBodyNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for( EnvironmentDivisionBodyNode node : body) {
			last = node.executeGeneric(frame);
		}
		return last;
	}

}
