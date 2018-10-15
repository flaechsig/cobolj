package de.cobolj.nodes;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName="ProcedureDivision")
public class ProcedureDivisionNode extends CobolNode {
	@Child
	private ProcedureDivisionBodyNode bodyNode;
	// FIXME: Die restlichen Member ergänzen
	
	public void setBody(ProcedureDivisionBodyNode bodyNode) {
		this.bodyNode = bodyNode;
	}
	
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return bodyNode.executeGeneric(frame);
	}

}
