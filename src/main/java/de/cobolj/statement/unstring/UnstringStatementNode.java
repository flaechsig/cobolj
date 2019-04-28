package de.cobolj.statement.unstring;

import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.statement.unstring.UnstringStatementVisitor;
import de.cobolj.phrase.PhraseNode;
import de.cobolj.runtime.NumericPicture;
import de.cobolj.runtime.Picture;
import de.cobolj.statement.StatementNode;

/**
 * @see UnstringStatementVisitor
 * 
 * @author flaechsig
 *
 */
@NodeInfo(shortName = "UnstringStatement")
public class UnstringStatementNode extends StatementNode {
	@Child
	private PictureNode sending;
	@Children
	private UnstringDelimitNode[] delimiters;
	private UnstringInto[] unstringInto;
	private UnstringDelimitNode actDelim;
	private int position;
	@Child
	private PictureNode tallying;
	@Child
	private PictureNode pointer;
	@Child
	private PhraseNode onOverflow;
	@Child
	private PhraseNode notOnOverflow;

	public UnstringStatementNode(PictureNode sending, List<UnstringDelimitNode> delimiters,
			List<UnstringInto> unstringInto, PictureNode tallying, PictureNode pointer, PhraseNode onOverflow,
			PhraseNode notOnOverflow) {
		this.sending = sending;
		this.delimiters = delimiters.toArray(new UnstringDelimitNode[] {});
		this.unstringInto = unstringInto.toArray(new UnstringInto[] {});
		this.tallying = tallying;
		this.pointer = pointer;
		this.onOverflow = onOverflow;
		this.notOnOverflow = notOnOverflow;
	}

	/**
	 * Für jedes Ziel wird aus der Quelle der entsprechende Textabschnitt extrahiert
	 * und zugewiesen.
	 */
	@Override
	public StatementNode executeGeneric(VirtualFrame frame) {
		for (UnstringDelimitNode delimit : delimiters) {
			// initialisierung
			delimit.executeGeneric(frame);
		}
		NumericPicture tallyPic = null;
		if (tallying != null) {
			tallyPic = (NumericPicture) tallying.executeGeneric(frame);
		}
		String quelle = sending.executeGeneric(frame).toString();

		position = 0;
		if (pointer != null) {
			NumericPicture pointerPic = (NumericPicture) pointer.executeGeneric(frame);
			position = pointerPic.getBigDecimal().intValue() - 1;
		}

		// Erster Overflow-Check (Pointer prüfen)
		if (onOverflow != null && (position < 0 || position >= quelle.length())) {
			onOverflow.executeGeneric(frame);
			return this;
		}

		for (UnstringInto into : unstringInto) {
			Picture pic = getContext().getPicture(frame, into.receiver);
			int newPos = nextEndPostion(quelle, pic.getSize());
			pic.setValue(quelle.substring(position, newPos));
			if (into.delimiter != null) {
				getContext().getPicture(frame, into.delimiter).setValue(actDelim.getDelim());
			}
			if (into.count != null) {
				getContext().getPicture(frame, into.count).setValue(newPos - position);
			}
			position = newPos;
			if (tallyPic != null) {
				tallyPic.setValue(tallyPic.getBigDecimal().longValue() + 1);
			}
		}
		
		// Zweiter Overflow-Check. Sending-Feld hat noch Zeichen übrig
		if (onOverflow != null && (position < quelle.length())) {
			onOverflow.executeGeneric(frame);
			return this;
		}
		if (notOnOverflow != null) {
			notOnOverflow.executeGeneric(frame);
		}

		return this;
	}

	private int nextEndPostion(String quelle, int defaultSize) {
		// Wenn keine Delimiter gegeben sind, dann sofort zurück
		if (delimiters.length == 0) {
			return position + defaultSize;
		}

		// Start-Postion korrigieren
		if (actDelim != null) {
			do {
				position += actDelim.getSize();
			} while (actDelim.isAll() && quelle.substring(position).startsWith(actDelim.getDelim()));
		}

		int result = Integer.MAX_VALUE;
		for (UnstringDelimitNode delim : delimiters) {
			int tmpStart = position;
			int tmpIdx = delim.nextEndPosition(quelle, tmpStart);
			if (tmpIdx < result) {
				result = tmpIdx;
				actDelim = delim;
			}
		}
		return result;
	}

}
