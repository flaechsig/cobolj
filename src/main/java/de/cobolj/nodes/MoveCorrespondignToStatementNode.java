package de.cobolj.nodes;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.runtime.Picture;
import de.cobolj.statement.StatementNode;

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
	@Child
	private PictureNode sending;
	/** Liste der Slots, in die das Quellfeld übertragen wird. */
	@Children
	private final PictureNode[] receiving;

	public MoveCorrespondignToStatementNode(PictureNode sending, List<PictureNode> receiving) {
		this.sending = sending;
		this.receiving = receiving.toArray(new PictureNode[] {});
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		Picture sendPic = sending.executeGeneric(frame);
		for (PictureNode slot : receiving) {
			Picture pic = slot.executeGeneric(frame);
			pic.clear();
			pic.setValue(sendPic);
		}
		return receiving;
	}

}
