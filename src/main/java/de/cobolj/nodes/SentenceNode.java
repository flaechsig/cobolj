package de.cobolj.nodes;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.parser.statement.nextsentence.NextSentenceExcetion;
import de.cobolj.statement.StatementNode;
import de.cobolj.statement.gotostmt.GotoException;

/**
 * Ein Sentence (Satz) definiert einen zusammengehörigen Block von 0 - n
 * Statements.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "Sentence")
public class SentenceNode extends StructureNode {
	@Children
	protected final StatementNode[] statements;

	public SentenceNode(List<StatementNode> statements) {
		this.statements = statements.toArray(new StatementNode[] {});
	}

	/**
	 * Führt einen Sentence aus.
	 *
	 * @param frame Context des Aufrufs
	 * @return Return-Wert des letzten ausgeführten Statements
	 * @throws GotoException Ein Goto-Befehl wurde ausgeführt
	 * @throws NextSentenceExcetion Ein Next-Sentence-Befehl wurde ausgeführt
	 */
	@Override
	public Object executeGeneric(VirtualFrame frame) throws GotoException, NextSentenceExcetion {
		Object last = null;
		for (StatementNode node : statements) {
			last = node.executeGeneric(frame);
		}
		return last;
	}
}
