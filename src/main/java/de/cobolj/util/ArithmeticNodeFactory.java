package de.cobolj.util;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.NumberNode;
import de.cobolj.nodes.NumberStorageNode;
import de.cobolj.nodes.ReadElementaryItemNode;
import de.cobolj.parser.Cobol85Parser.IdentifierContext;
import de.cobolj.parser.Cobol85Parser.LiteralContext;

/**
 * Separate Factory für arithmetische Werte.
 * 
 * @author flaechsig
 *
 */
public class ArithmeticNodeFactory {
	private ArithmeticNodeFactory() {}

	public static NumberNode create(LiteralContext literal, IdentifierContext identifier) {
		ExpressionNode node =  ExpressionNodeFactory.create(literal,identifier);
		if(node instanceof ReadElementaryItemNode) {
			return new NumberStorageNode((ReadElementaryItemNode) node);
		} else {
			return (NumberNode) node;
		}
	}

}
