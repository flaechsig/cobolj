package de.cobolj.nodes;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName="CompilationUnit")
public class CompilationUnitNode extends CobolNode {
	@Children
	private final ProgramUnitNode[] programUnit;

	public CompilationUnitNode(List<ProgramUnitNode> programUnit) {
		this.programUnit = programUnit.toArray(new ProgramUnitNode[0]);
	}
	
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for(ProgramUnitNode node : programUnit) {
			last = node.executeGeneric(frame);
		}
		return last;
	}

}
