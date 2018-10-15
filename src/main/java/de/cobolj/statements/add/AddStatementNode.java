package de.cobolj.statements.add;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.parser.AddImplNode;
import de.cobolj.phrase.SizeOverflowException;
import de.cobolj.phrase.SizePhraseNode;
import de.cobolj.statements.StatementNode;

/**
 * Ausführung des Add-Statements. Die Addtion wird simuliert und im Anschluss
 * anhand des Überlaufs geprüft, ob eine der Phrases durchlaufen werden muss.
 * 
 * Gewöhnungsdürftige Semantik: Wenn ein Fehler auftritt, dann wird der Block
 * exakt einmal ausgeführt. Für die betroffenen Ergebniss-Objekte wird der alte
 * Wert beibehalten. Alle Ergebnisse, die nicht wiedersprechen werden
 * übernommen.
 * 
 * @author flaechsig
 *
 */
public class AddStatementNode extends StatementNode {

	/** Eine Add-Implementierung der drei unterschiedlichen Möglichkeiten */
	@Child
	private AddImplNode add;
	/** Optionaler Error-Block */
	@Child
	private SizePhraseNode errorPhrase;
	/** Optionaler Success-Block */
	@Child
	private SizePhraseNode successPhrase;

	public AddStatementNode(AddImplNode add, SizePhraseNode errorPhrase, SizePhraseNode successPhrase) {
		this.add = add;
		this.errorPhrase = errorPhrase;
		this.successPhrase = successPhrase;
	}

	/**
	 * @see AddStatementNode
	 * 
	 * @param frame Standard-Frame zur Ausführung.
	 * @return Liefert null, da es kein Ergeniswert hat.
	 */
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		try {
			this.add.executeGeneric(frame);
			if (successPhrase != null) {
				successPhrase.executeGeneric(frame);
			}
		} catch (SizeOverflowException e) {
			if (errorPhrase != null) {
				this.errorPhrase.executeGeneric(frame);
			}
		}
		return null;
	}

}
