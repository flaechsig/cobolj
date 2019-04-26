package de.cobolj.nodes;

import java.util.List;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.runtime.Picture;
import de.cobolj.statement.StatementNode;

/**
 * Implementierung des "einfachen" MOVE ... TO ...
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "MoveTo")
public class MoveToStatementNode extends StatementNode {

	/** Quellfeld für den Move-Befehl */
	@Child
	private ExpressionNode sending;
	/** Liste der Slots, in die das Quellfeld übertragen wird. */
	private final String[] receiving;

	public MoveToStatementNode(ExpressionNode sending, List<String> receiving) {
		this.sending = sending;
		this.receiving = receiving.toArray(new String[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		for (String slot : receiving) {
			Picture pic = getContext().getPicture(frame, slot);
			pic.setValue(sending.executeGeneric(frame));
		}
		return receiving;
	}

}
