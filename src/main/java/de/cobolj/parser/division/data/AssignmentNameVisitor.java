package de.cobolj.parser.division.data;

import de.cobolj.nodes.LiteralNode;
import de.cobolj.nodes.StringNode;
import de.cobolj.parser.Cobol85BaseVisitor;
import de.cobolj.parser.Cobol85Parser.IdentifierContext;

public class AssignmentNameVisitor extends Cobol85BaseVisitor<LiteralNode> {
	
	@Override
	public LiteralNode visitIdentifier(IdentifierContext ctx) {
		String dataName = System.getProperty(ctx.getText());
		if( dataName == null) {
			throw new RuntimeException("Data Definition of ("+ctx.getText()+") not found");
		}
		return new StringNode(dataName);
	}

}
