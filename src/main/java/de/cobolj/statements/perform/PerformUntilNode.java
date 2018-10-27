package de.cobolj.statements.perform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.ExpressionNode;

/**
 * Implementiert das Statement PERFORM XXXX TIMES ... END PERFORM.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName="PerformTimes")
public class PerformUntilNode extends PerformTypeNode {
	/** Kennzeichen, ob die Schleifenbedingung vor dem Schleifen-Body geprüft werden soll */
	private final boolean testBefore;
	/** Schleifenzähler */
	@Child
	private ExpressionNode until;
	
	public PerformUntilNode(boolean testBefore, ExpressionNode until, PerformStatementNode perform) {
		super(perform);
		this.until = until;
		this.testBefore = testBefore;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {

		if(!testBefore) {
			// Ersten durchgang immer ausführen
			perform.executeGeneric(frame);
		}
		while(!(boolean) until.executeGeneric(frame))  {
			perform.executeGeneric(frame);
		}
		
		return null;
	}

}
