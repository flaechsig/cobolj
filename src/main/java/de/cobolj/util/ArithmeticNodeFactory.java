package de.cobolj.util;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.NumberNode;
import de.cobolj.nodes.NumberStorageNode;
import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.LiteralVisitor;
import de.cobolj.parser.Cobol85Parser.IdentifierContext;
import de.cobolj.parser.Cobol85Parser.LiteralContext;
import de.cobolj.parser.IdentifierVisitor;

/**
 * Separate Factory für arithmetische Werte.
 * 
 * @author flaechsig
 *
 */
public class ArithmeticNodeFactory {
	private ArithmeticNodeFactory() {
	}

	public static NumberNode create(LiteralContext literal, IdentifierContext identifier) {
		NumberNode result = null;
		if (literal != null) {
			result =  (NumberNode) literal.accept(LiteralVisitor.INSTANCE);
		} else if (identifier != null) {
			result = new NumberStorageNode( identifier.accept(IdentifierVisitor.INSTANCE)) ;
		} 
		return result;
	}

}
