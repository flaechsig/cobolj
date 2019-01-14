package de.cobolj.parser.statement.perform;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.statement.perform.PerformTypeNode;

public class PerformVaryingNode extends PerformTypeNode {
	
	public PerformVaryingNode(ExpressionNode perform) {
		super(perform);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return perform.executeGeneric(frame);
	}

}
