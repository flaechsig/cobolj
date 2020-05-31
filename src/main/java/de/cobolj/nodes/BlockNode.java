package de.cobolj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import de.cobolj.statement.StatementNode;
import de.cobolj.statement.gotostmt.GotoException;

import java.util.List;

/**
 * Ansammlung von Statements, die ausgeführt werden. Diese Klasse ist damit sehr ähnlich zu SentenceNode.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="Block")
public class BlockNode extends StructureNode {
	
	@Children
	protected final StatementNode[] statements;
	
	public BlockNode(List<StatementNode> statements) {
		this.statements = statements.toArray(new StatementNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) throws GotoException {
		for(StatementNode stmt : statements) {
			stmt.executeGeneric(frame);
		}
		return null;
	}

}
