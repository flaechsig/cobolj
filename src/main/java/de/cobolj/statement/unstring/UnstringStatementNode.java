package de.cobolj.statement.unstring;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import de.cobolj.nodes.PictureNode;
import de.cobolj.parser.statement.unstring.UnstringStatementVisitor;
import de.cobolj.phrase.PhraseNode;
import de.cobolj.runtime.NumericPicture;
import de.cobolj.runtime.Picture;
import de.cobolj.statement.StatementNode;
import de.cobolj.statement.gotostmt.GotoException;

import java.util.List;

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
	@Children
	private final PictureNode[] dataReceivingField;
	@Children
	private final PictureNode[] delimiterReceivingField;
	@Children 
	private final PictureNode[] dataCountField;
	private UnstringDelimitNode actDelim;
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
		this.dataReceivingField = new PictureNode[unstringInto.size()];
		this.delimiterReceivingField = new PictureNode[unstringInto.size()];
		this.dataCountField = new PictureNode[unstringInto.size()];
		this.tallying = tallying;
		this.pointer = pointer;
		this.onOverflow = onOverflow;
		this.notOnOverflow = notOnOverflow;
		
		for(int i=0; i<unstringInto.size();i++) {
			dataReceivingField[i] = unstringInto.get(i).receiver;
			delimiterReceivingField[i] = unstringInto.get(i).delimiter;
			dataCountField[i] = unstringInto.get(i).count;
		}
	}

	/**
	 * Für jedes Ziel wird aus der Quelle der entsprechende Textabschnitt extrahiert
	 * und zugewiesen.
	 */
	@Override
	public StatementNode executeGeneric(VirtualFrame frame) throws GotoException {
		for (UnstringDelimitNode delimit : delimiters) {
			// initialisierung
			delimit.executeGeneric(frame);
		}
		NumericPicture tallyPic = null;
		if (tallying != null) {
			tallyPic = (NumericPicture) tallying.executeGeneric(frame);
		}

		String quelle = sending.executeGeneric(frame).toString();
		int position=0;
		if (pointer != null) {
			NumericPicture pointerPic = (NumericPicture) pointer.executeGeneric(frame);
			position = pointerPic.getBigDecimal().intValue() - 1;
		}

		// Erster Overflow-Check (Pointer prüfen)
		if (onOverflow != null && (position < 0 || position >= quelle.length())) {
			onOverflow.executeGeneric(frame);
			return this;
		}

		quelle = quelle.substring(position);

		for (int i=0; i<dataReceivingField.length; i++) {
			Picture receiving = (Picture) dataReceivingField[i].executeGeneric(frame);
			Picture delimiter = null;
			if(delimiterReceivingField[i]!=null) {
				delimiter = (Picture) delimiterReceivingField[i].executeGeneric(frame);
			}
			Picture counter = null;
			if(dataCountField[i]!=null) {
				counter = (Picture) dataCountField[i].executeGeneric(frame);
			}
			
			int size;
			if(delimiters.length==0) {
				// Nach der Feldgröße des Empfängers schneiden
				size = receiving.getSize();
				receiving.setValue(quelle.substring(0, size));
				quelle = quelle.substring(size);
			} else {
				// Delimiter verwenden und nächste Position finden
				size = nextPosition(quelle);
				if(size==Integer.MAX_VALUE) {
					size = Math.max(quelle.length(), receiving.getSize());
				}
				receiving.setValue(quelle.substring(0, size));
				quelle = quelle.substring(size);
				if( actDelim != null && quelle.startsWith(actDelim.delim)) {
					quelle = quelle.substring(actDelim.getSize());
				}
				while(actDelim != null && this.actDelim.isAll() && quelle.startsWith(actDelim.delim)) {
					quelle = quelle.substring(actDelim.getSize());
				} 
			}
			if(actDelim != null) {
				if(delimiter!=null) {
					delimiter.setValue(actDelim.delim);
				}
				if(counter != null) {
					counter.setValue(size);
				}
			}
			
			if (tallyPic != null) {
				tallyPic.setValue(tallyPic.getBigDecimal().longValue() + 1);
			}
		}

		// Zweiter Overflow-Check. Sending-Feld hat noch Zeichen übrig
		if (onOverflow != null && !quelle.isEmpty()) {
			onOverflow.executeGeneric(frame);
			return this;
		}
		if (notOnOverflow != null) {
			notOnOverflow.executeGeneric(frame);
		}

		return this;
	}

	/** Ermittelt in dem übergebenen String die nächste Position des "kleinsten" delimiters. */
	private int nextPosition(String quelle) {
		int result = Integer.MAX_VALUE;
		actDelim = null;
		for(UnstringDelimitNode delimiter : delimiters) {
			String delimiterString = delimiter.delim;
			int idx = quelle.indexOf(delimiterString);
			if(idx < result && idx > -1) {
				result = idx;
				actDelim = delimiter;
			}
		}
		return result;
	}

}
