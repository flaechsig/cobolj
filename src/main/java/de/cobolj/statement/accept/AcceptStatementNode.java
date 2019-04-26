package de.cobolj.statement.accept;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.runtime.Picture;
import de.cobolj.statement.StatementNode;

/**
 * Implementierung des Accept-Statements.
 * 
 * Aus einer Input-Quelle wird der Wert für das Accept-Statement ausgelesen und zurück geliefert.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="Acccept")
public class AcceptStatementNode extends StatementNode {
	
	@Child
	private InputNode input;
	private final String slot;
	private final boolean rounded;
	
	public AcceptStatementNode(InputNode input, String slot, boolean rounded) {
		this.input = input;
		this.slot = slot;
		this.rounded = rounded;
	}
	@Override
	public Picture executeGeneric(VirtualFrame frame) {
		Picture pic = getContext().getPicture(frame, slot);
		pic.setValue(input.executeGeneric(frame));
		// TODO: Was ist mit rounded
		return pic;
	}

}
