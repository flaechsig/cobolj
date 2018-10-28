package de.cobolj.statements.perform;

import de.cobolj.nodes.ExpressionNode;
import de.cobolj.statements.StatementNode;

/**
 * Abstrakte Oberklasse für die verschiedenen Perform-Typen.
 * 
 * @author flaechsig
 *
 */
public abstract class PerformTypeNode extends StatementNode {

	@Child
	protected ExpressionNode perform;
	
	public PerformTypeNode(ExpressionNode perform) {
		this.perform = perform;
	}
}
