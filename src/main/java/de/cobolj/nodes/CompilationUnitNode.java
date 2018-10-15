package de.cobolj.nodes;

import java.util.Collection;

import com.oracle.truffle.api.frame.VirtualFrame;

public class CompilationUnitNode extends CobolNode {
	@Children
	private final ProgramUnitNode[] programUnit;

	public CompilationUnitNode(Collection<ProgramUnitNode> programUnit) {
		this.programUnit = programUnit.toArray(new ProgramUnitNode[] {});
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
