package de.cobolj.nodes;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName="Data Division")
public class DataDivisionNode extends CobolNode {

	@Children
	private final DataDivisionSectionNode[] sections;
	
	public DataDivisionNode(List<DataDivisionSectionNode> sections) {
		this.sections = sections.toArray(new DataDivisionSectionNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for(DataDivisionSectionNode section : sections) {
			last = section.executeGeneric(frame);
		}
		return last;
	}

}
