package de.cobolj.nodes;

import java.util.List;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.runtime.Picture;
import de.cobolj.statement.StatementNode;
import de.cobolj.util.FrameUtil;

/**
 * Implementierung des "einfachen" MOVE CORR ... TO ...
 * 
 * Das ist eine Gruppen-Kopierfunktion, die auf Namensgleichheit abzielt. Bei
 * gleichen Namen werden die Daten von der Quelle ins Ziel kopiert. Die Typen
 * müssen dabei inhaltlich übertragbar sein.
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "MoveCorrTo")
public class MoveCorrespondignToStatementNode extends StatementNode {

	/** Quellfeld für den Move-Befehl */
	private FrameSlot sending;
	/** Liste der Slots, in die das Quellfeld übertragen wird. */
	private final FrameSlot[] receiving;

	public MoveCorrespondignToStatementNode(FrameSlot sending, List<FrameSlot> receiving) {
		this.sending = sending;
		this.receiving = receiving.toArray(new FrameSlot[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Picture sendPic = FrameUtil.getPicture(frame, sending);
		for (FrameSlot slot : receiving) {
			Picture pic = FrameUtil.getPicture(frame, slot);
			pic.clear();
			pic.setValue(sendPic);
		}
		return receiving;
	}

}
