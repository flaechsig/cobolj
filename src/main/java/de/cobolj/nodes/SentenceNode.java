package de.cobolj.nodes;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.statements.StatementNode;

/**
 * Ein Sentence (Satz) definiert einen zusammengehörigen Block von 0 - n Statements.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="Sentence")
public class SentenceNode extends CobolNode {
	@Children
	private final StatementNode statements[];
	
	public SentenceNode(List<StatementNode> statements) {
		this.statements = statements.toArray(new StatementNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for(StatementNode node : statements) {
			last = node.executeGeneric(frame);
		}
		return last;
	}


}
