package de.cobolj.statements.perform;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.statements.StatementNode;

/**
 * Implementierung das Perform-Inline-Statement.
 * 
 * Die Inline-Statements werden in einem speziellen PerformTypeNode verpackt, um
 * die Ausführungsbedingung abbilden zu können.
 * 
 * @author flaechsig
 *
 */
public class PerformInlineStatementNode extends PerformStatementNode {

	/** Auszuführende Perform-Variante */
	@Children
	private final StatementNode[] statements;

	public PerformInlineStatementNode(List<StatementNode> statements) {
		this.statements = statements.toArray( new StatementNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		for(StatementNode stmt : statements) {
			stmt.executeGeneric(frame);
		}
		return null;
	}

}
