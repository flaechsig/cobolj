package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Implementiert das Statement PERFORM XXXX. (ohne Schleife usw.)
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "PerformTimes")
public class PerformOneTimeNode extends PerformTypeNode {

	public PerformOneTimeNode(PerformStatementNode perform) {
		super(perform);
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		perform.executeGeneric(frame);
		return null;
	}
}
