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
	@Children
	private final PictureNode[] receiving;

	public MoveToStatementNode(ExpressionNode sending, List<PictureNode> receiving) {
		this.sending = sending;
		this.receiving = receiving.toArray(new PictureNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		for (PictureNode slot : receiving) {
			Picture pic = slot.executeGeneric(frame);
			pic.setValue(sending.executeGeneric(frame));
		}
		return receiving;
	}

}
