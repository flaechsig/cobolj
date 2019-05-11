package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.division.environtment.EnvironmentDivisionNode;
import de.cobolj.division.identification.IdentificationDivisionNode;

@NodeInfo(shortName="ProgramUnit")
public class ProgramUnitNode extends CobolNode {
	@Child
	IdentificationDivisionNode identificationDivision;
	@Child
	private ProcedureDivisionNode procedureDivision;
	@Child
	private DataDivisionNode dataDivision;
	@Child
	private EnvironmentDivisionNode environmentDivision;
	
	public ProgramUnitNode(IdentificationDivisionNode identificationDivision,
			EnvironmentDivisionNode environmentDivision, DataDivisionNode dataDivision,
			ProcedureDivisionNode procedureDivision) {
		this.identificationDivision = identificationDivision;
		this.environmentDivision = environmentDivision;
		this.dataDivision = dataDivision;
		this.procedureDivision = procedureDivision;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object result = null;
		
		result = NodeHelper.excecuteGeneric(identificationDivision, result, frame);
		result = NodeHelper.excecuteGeneric(dataDivision, result, frame);
		result = NodeHelper.excecuteGeneric(environmentDivision, result, frame);
		result = NodeHelper.excecuteGeneric(procedureDivision, result, frame);
		
		return result;
	}
}
