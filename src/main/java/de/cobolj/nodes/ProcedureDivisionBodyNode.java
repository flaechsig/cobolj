package de.cobolj.nodes;

import java.util.Collection;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "ProcedureDivisionBody")
public class ProcedureDivisionBodyNode extends CobolNode {
	// FIXME: Prüfen, ob die Struktur der Knoten sinnvoll aufgebaut ist
	@Children
	private final StructureNode[] paragraphsOrProcedureSection;

	public ProcedureDivisionBodyNode(Collection<StructureNode> paragraphsOrProcedureSection) {
		this.paragraphsOrProcedureSection = paragraphsOrProcedureSection.toArray(new StructureNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for (CobolNode childNode : paragraphsOrProcedureSection) {
			last = childNode.executeGeneric(frame);
		}
		return last;
	}

}
