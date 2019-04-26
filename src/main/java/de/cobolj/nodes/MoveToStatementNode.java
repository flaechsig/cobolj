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
	private final FrameSlot[] receiving;

	public MoveToStatementNode(ExpressionNode sending, List<FrameSlot> receiving) {
		this.sending = sending;
		this.receiving = receiving.toArray(new FrameSlot[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		for (FrameSlot slot : receiving) {
//			Picture pic = FrameUtil.getPicture(frame, slot);
			Picture pic = getContext().getPicture(slot.getIdentifier().toString());
			pic.setValue(sending.executeGeneric(frame));
		}
		return receiving;
	}

}
