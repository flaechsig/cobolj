package de.cobolj.parser;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.CompilationUnitNode;
import de.cobolj.nodes.ProgramUnitNode;

/** 
 * Zweite Ebene des Parsers. 
 * 
 * compilationUnit: programUnit+ ;
 * 
 * @author flaechsig
 *
 */
public class CompilationUnitVisitor extends Cobol85BaseVisitor<CompilationUnitNode> {

	/**
	 * Zweite Ebene im Parse-Baum.... Noch keine wirkliche Bedeutung
	 */
	@Override
	public CompilationUnitNode visitCompilationUnit(Cobol85Parser.CompilationUnitContext ctx) {
		ProgramUnitVisitor programUnitVisitor = new ProgramUnitVisitor();
		List<ProgramUnitNode> programUnitNodes = ctx.programUnit()
				.stream()
				.map(programUnit ->  programUnit.accept(programUnitVisitor))
				.collect(Collectors.toList());
		
		return new CompilationUnitNode(programUnitNodes);
	}
}
