package de.cobolj.statement;

import com.oracle.truffle.api.frame.VirtualFrame;

import de.cobolj.parser.statement.add.MathImplNode;
import de.cobolj.phrase.SizeOverflowException;
import de.cobolj.phrase.PhraseNode;

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
public class MathStatementNode extends StatementNode {

	/** Eine Add-Implementierung der drei unterschiedlichen Möglichkeiten */
	@Child
	private MathImplNode math;
	/** Optionaler Error-Block */
	@Child
	private PhraseNode errorPhrase;
	/** Optionaler Success-Block */
	@Child
	private PhraseNode successPhrase;

	public MathStatementNode(MathImplNode math, PhraseNode errorPhrase, PhraseNode successPhrase) {
		this.math = math;
		this.errorPhrase = errorPhrase;
		this.successPhrase = successPhrase;
	}

	/**
	 * @see MathStatementNode
	 * 
	 * @param frame Standard-Frame zur Ausführung.
	 * @return Liefert null, da es kein Ergeniswert hat.
	 */
	@Override
	public Object executeGeneric(VirtualFrame frame) {
		try {
			this.math.executeGeneric(frame);
			if (successPhrase != null) {
				successPhrase.executeGeneric(frame);
			}
		} catch (SizeOverflowException e) {
			if (errorPhrase != null) {
				this.errorPhrase.executeGeneric(frame);
			}
		}
		return this;
	}

}
