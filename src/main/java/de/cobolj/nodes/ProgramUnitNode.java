package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName="ProgramUnit")
public class ProgramUnitNode extends CobolNode {
	@Child
	private ProcedureDivisionNode procedureDivision;
	@Child
	private DataDivisionNode dataDivision;
	
	public void setProcedureDivision(ProcedureDivisionNode division) {
		this.procedureDivision = division;
	}

	public void setDataDivision(DataDivisionNode dataDivisionNode) {
		this.dataDivision = dataDivisionNode;
	}
	
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object result = null;
		if(dataDivision != null) {
			result = dataDivision.executeGeneric(frame);
		}
		if(procedureDivision != null) {
			result = procedureDivision.executeGeneric(frame);
		}
		return result;
	}


}