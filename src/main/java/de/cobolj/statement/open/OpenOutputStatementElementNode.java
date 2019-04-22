package de.cobolj.statement.open;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Dieses Komando für das Open-Statement öffnet eine Reihe von Output-Elementen.
 * Aufgrund der aktuellen Technologie wird es sich dabei wohl nur um Dateien handeln ;-)
 * Dazu wird für jeden Input ein FileDescriptor angelegt, über den auf den zugrundeliegenden
 * Datenstrom zugegriffen werden kann.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="OpenOutputStatementElement")
public class OpenOutputStatementElementNode extends OpenStatementElementNode {

	@Children
	private final OpenOutputNode[] outputs;

	public OpenOutputStatementElementNode(List<OpenOutputNode> outputs) {
		this.outputs = outputs.toArray(new OpenOutputNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for( OpenOutputNode node : outputs) {
			last = node.executeGeneric(frame);
		}
		return last;
	}

}
