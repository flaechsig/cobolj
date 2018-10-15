package de.cobolj.parser;

import de.cobolj.nodes.StringNode;

/**
 * 
 * qualifiedDataNameFormat1:  (dataName | conditionName)(qualifiedInData+ inFile? | inFile)?
 * 
 * @author flaechsig
 *
 */
public class QualifiedDataNameFormat1Visitor extends Cobol85BaseVisitor<StringNode> {

	public static final QualifiedDataNameFormat1Visitor INSTANCE = new QualifiedDataNameFormat1Visitor();
	
	private QualifiedDataNameFormat1Visitor() {	}

	@Override
	public StringNode visitQualifiedDataNameFormat1(Cobol85Parser.QualifiedDataNameFormat1Context ctx) {
		// FIXME: Vervollständigen
		StringNode result = null;
		if(ctx.dataName() != null) {
			result = ctx.dataName().accept(DataNameVisitor.INSTANCE);
		} else {
			throw new RuntimeException("Not implemented");
		}
		
		if(!ctx.qualifiedInData().isEmpty() || ctx.inFile() != null) {
			throw new RuntimeException("Condition not implemented");
		}
		return result;
	}
}
