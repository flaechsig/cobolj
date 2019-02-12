package de.cobolj.parser;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Dieses Komando für das Open-Statement öffnet eine Reihe von Input-Elementen.
 * Aufgrund der aktuellen Technologie wird es sich dabei wohl nur um Dateien handeln ;-)
 * Dazu wird für jeden Input ein FileDescriptor angelegt, über den auf den zugrundeliegenden
 * Datenstrom zugegriffen werden kann.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="OpentInputStatementElement")
public class OpentInputStatementElementNode extends OpenStatementElementNode {

	@Children
	private final OpenInputNode[] inputs;

	public OpentInputStatementElementNode(List<OpenInputNode> inputs) {
		this.inputs = inputs.toArray(new OpenInputNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for( OpenInputNode node : inputs) {
			last = node.executeGeneric(frame);
		}
		return last;
	}

}
