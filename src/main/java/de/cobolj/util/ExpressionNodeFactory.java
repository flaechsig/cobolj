package de.cobolj.util;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.nodes.ReadElementaryItemNodeGen;
import de.cobolj.parser.Cobol85Parser.IdentifierContext;
import de.cobolj.parser.Cobol85Parser.IntegerLiteralContext;
import de.cobolj.parser.Cobol85Parser.LiteralContext;
import de.cobolj.parser.IdentifierVisitor;
import de.cobolj.parser.LiteralVisitor;
import de.cobolj.parser.NumericalLiteralVisitor;

public class ExpressionNodeFactory {
	private ExpressionNodeFactory() {}
	
	/**
	 * Erstellt eine ExpressionNode.
	 * 
	 * @param literal Ein LiteralContext oder null
	 * @param identifier Ein IdentifierContext oder null
	 * @return ExpressionNode oder null
	 */
	public static ExpressionNode create(LiteralContext literal, IdentifierContext identifier) {
		if(literal != null) {
			return literal.accept(LiteralVisitor.INSTANCE);
		} else if(identifier !=null) {
			return ReadElementaryItemNodeGen.create(identifier.accept(IdentifierVisitor.INSTANCE));
		} else {
			return null;
		}
	}

	/**
	 * @see ExpressionNodeFactory#create(LiteralContext, IdentifierContext)
	 */
	public static ExpressionNode create(IntegerLiteralContext literal, IdentifierContext identifier) {
		if(literal != null) {
			return literal.accept(new NumericalLiteralVisitor());
		} else if(identifier !=null) {
			return ReadElementaryItemNodeGen.create(identifier.accept(IdentifierVisitor.INSTANCE));
		} else {
			return null;
		}
	}

}
