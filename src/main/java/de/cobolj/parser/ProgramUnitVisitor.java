package de.cobolj.parser;

import de.cobolj.division.environtment.EnvironmentDivisionNode;
import de.cobolj.nodes.DataDivisionNode;
import de.cobolj.nodes.ProcedureDivisionNode;
import de.cobolj.nodes.ProgramUnitNode;
import de.cobolj.parser.division.data.DataDivisionVisitor;
import de.cobolj.parser.division.environment.EnvironmentDivisionVisitor;
import de.cobolj.parser.division.procedure.ProcedureDivisionVisitor;

/**
 * Oberster Knoten wir ein Cobol-Programm. Ab diesen Knoten wird ein
 * vollständiges Cobol-Programm geparst.
 * 
 * programUnit: identificationDivision environmentDivision? dataDivision?
 * procedureDivision? programUnit* endProgramStatement?;
 * 
 * @author flaechsig
 *
 */
public class ProgramUnitVisitor extends Cobol85BaseVisitor<ProgramUnitNode> {

	@Override
	public ProgramUnitNode visitProgramUnit(Cobol85Parser.ProgramUnitContext ctx) {
//		ParserHelper.notImplemented(ctx.identificationDivision());
		
		EnvironmentDivisionNode environmentDivisionNode = null;
		ProcedureDivisionNode procedureDivisionNode = null;
		DataDivisionNode dataDivisionNode = null;

		if(ctx.environmentDivision()!=null) {
			environmentDivisionNode = ctx.environmentDivision().accept(new EnvironmentDivisionVisitor());
		}
		if(ctx.dataDivision() != null) {
			DataDivisionVisitor visitor = new DataDivisionVisitor();
			dataDivisionNode = ctx.dataDivision().accept(visitor);
		}
		if (ctx.procedureDivision() != null) {
			ProcedureDivisionVisitor visitor = new ProcedureDivisionVisitor();
			procedureDivisionNode = ctx.procedureDivision().accept(visitor);
		}

		ProgramUnitNode programUnit = new ProgramUnitNode();
		programUnit.setEnvironmentDivision(environmentDivisionNode);
		programUnit.setDataDivision(dataDivisionNode);
		programUnit.setProcedureDivision(procedureDivisionNode);

		return programUnit;
	}

}
