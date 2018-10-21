package de.cobolj.odes;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.nodes.PerformTypeNode;
import de.cobolj.statements.StatementNode;

/**
 * Implementierung des Perform-Statements
 * 
 * @author flaechsig
 *
 */
public class PerformStatementNode extends StatementNode {

	/** Auszuführende Perform-Variante */
	@Child
	private PerformTypeNode child;

	public PerformStatementNode(PerformTypeNode node) {
		this.child = node;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		return child.executeGeneric(frame);
	}

}
