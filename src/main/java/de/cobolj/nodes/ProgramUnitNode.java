package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.parser.EnvironmentDivisionNode;

@NodeInfo(shortName="ProgramUnit")
public class ProgramUnitNode extends CobolNode {
	@Child
	private ProcedureDivisionNode procedureDivision;
	@Child
	private DataDivisionNode dataDivision;
	private EnvironmentDivisionNode environmentDevision;
	
	public void setProcedureDivision(ProcedureDivisionNode division) {
		this.procedureDivision = division;
	}

	public void setDataDivision(DataDivisionNode dataDivisionNode) {
		this.dataDivision = dataDivisionNode;
	}
	
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object result = null;
		if(environmentDevision != null) {
			result = environmentDevision.executeGeneric(frame);
		}
		if(dataDivision != null) {
			result = dataDivision.executeGeneric(frame);
		}
		if(procedureDivision != null) {
			result = procedureDivision.executeGeneric(frame);
		}
		return result;
	}

	public void setEnvironmentDivision(EnvironmentDivisionNode environmentDivisionNode) {
		this.environmentDevision = environmentDivisionNode;
	}


}
