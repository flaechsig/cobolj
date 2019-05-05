package de.cobolj.parser.condition;

import java.util.List;

import de.cobolj.nodes.CompilationUnitNode;
import de.cobolj.nodes.ProgramUnitNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.ParserHelper;
import de.cobolj.parser.ProgramUnitVisitor;

/**
 * compilationUnit : programUnit+ ;
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
		List<ProgramUnitNode> programUnitNodes = ParserHelper.accept(ctx.programUnit(), new ProgramUnitVisitor());
		return new CompilationUnitNode(programUnitNodes);
	}
}
