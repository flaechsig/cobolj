package de.cobolj.parser.division.data;

import java.util.List;
import java.util.stream.Collectors;

import de.cobolj.nodes.DataDivisionNode;
import de.cobolj.nodes.DataDivisionSectionNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser;
import de.cobolj.parser.Cobol85Parser.DataDivisionContext;

/**
 * 
 * dataDivision: DATA DIVISION DOT_FS dataDivisionSection*
 * 
 * @author flaechsig
 *
 */
public class DataDivisionVisitor extends Cobol85BaseVisitor<DataDivisionNode> {

	@Override
	public DataDivisionNode visitDataDivision(Cobol85Parser.DataDivisionContext ctx) {
		DataDivisionSectionVisitor visitor = DataDivisionSectionVisitor.INSTANCE;
		List<DataDivisionSectionNode> sections = ctx.dataDivisionSection().stream()
				.map(division -> division.accept(visitor)).collect(Collectors.toList());
		return new DataDivisionNode(sections);
	}
}
