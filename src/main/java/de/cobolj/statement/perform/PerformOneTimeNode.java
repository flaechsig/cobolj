package de.cobolj.statement.perform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;

/**
 * Implementiert das Statement PERFORM XXXX. (ohne Schleife usw.)
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "PerformTimes")
public class PerformOneTimeNode extends PerformTypeNode {

	public PerformOneTimeNode(ExpressionNode perform) {
		super(perform);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		perform.executeGeneric(frame);
		return null;
	}
}
