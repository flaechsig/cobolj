package de.cobolj.parser;

import static de.cobolj.parser.ParserHelper.accept;
import static de.cobolj.parser.ParserHelper.notImplemented;

import de.cobolj.division.environtment.EnvironmentDivisionNode;
import de.cobolj.division.identification.IdentificationDivisionNode;
import de.cobolj.nodes.DataDivisionNode;
import de.cobolj.nodes.ProcedureDivisionNode;
import de.cobolj.nodes.ProgramUnitNode;
import de.cobolj.parser.division.data.DataDivisionVisitor;
import de.cobolj.parser.division.environment.EnvironmentDivisionVisitor;
import de.cobolj.parser.division.identification.IdentificationDivisionVisitor;
import de.cobolj.parser.division.procedure.ProcedureDivisionVisitor;

/**
 * 
 * programUnit : identificationDivision environmentDivision? dataDivision?
 * procedureDivision? programUnit* endProgramStatement? ; Oberster Knoten wir
 * ein Cobol-Programm. Ab diesen Knoten wird ein vollständiges Cobol-Programm
 * geparst.
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
		notImplemented(ctx.programUnit());
		notImplemented(ctx.endProgramStatement());
		
		IdentificationDivisionNode identificationDivision = accept(ctx.identificationDivision(),
				new IdentificationDivisionVisitor());
		EnvironmentDivisionNode environmentDivision = accept(ctx.environmentDivision(),
				new EnvironmentDivisionVisitor());
		DataDivisionNode dataDivision = accept(ctx.dataDivision(), new DataDivisionVisitor());
		ProcedureDivisionNode procedureDivision = accept(ctx.procedureDivision(),
				new ProcedureDivisionVisitor());

		ProgramUnitNode programUnit = new ProgramUnitNode(identificationDivision, environmentDivision, dataDivision, procedureDivision);

		return programUnit;
	}

}
