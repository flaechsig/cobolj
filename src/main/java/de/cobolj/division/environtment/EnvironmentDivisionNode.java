package de.cobolj.division.environtment;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;

@NodeInfo(shortName="EnvironmentDivision")
public class EnvironmentDivisionNode extends CobolNode {
	/** Body der Environment-Section */
	@Children
	private final EnvironmentDivisionBodyNode[] body;

	public EnvironmentDivisionNode(List<EnvironmentDivisionBodyNode> body) {
		this.body = body.toArray(new EnvironmentDivisionBodyNode[0]);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = this;
		for( EnvironmentDivisionBodyNode node : body) {
			last = node.executeGeneric(frame);
		}
		return last;
	}

}
