package de.cobolj.phrase;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.CobolNode;
import de.cobolj.statement.StatementNode;

/**
 * Eine Instanz dieser Klasse ist bei arithmetischen Funktionen optional und
 * wird im Erfolgs- oder Feherlfall nach der Ausführung der Operation zusätzlich
 * ausgeführt.
 * 
 * Sie kann mehrere Statements enthalten, die innerhalb der SIZE ERROR- bzw. NOT
 * SIZE ERROR Phrase ausgeführt werden.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "NotOnSizeError")
public class PhraseNode extends CobolNode {

	@Children
	private final StatementNode[] statements;

	public PhraseNode(List<StatementNode> statements) {
		this.statements = statements.toArray(new StatementNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Object last = null;
		for (StatementNode stmt : statements) {
			last = stmt.executeGeneric(frame);
		}
		return last;
	}

}
