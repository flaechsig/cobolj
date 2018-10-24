package de.cobolj.nodes;

import de.cobolj.statements.StatementNode;

/**
 * Abstrakte Oberklasse für die verschiedenen Perform-Typen.
 * 
 * @author flaechsig
 *
 */
public abstract class PerformTypeNode extends StatementNode {

	@Child
	protected PerformStatementNode perform;
	
	public PerformTypeNode(PerformStatementNode perform) {
		this.perform = perform;
	}
}
