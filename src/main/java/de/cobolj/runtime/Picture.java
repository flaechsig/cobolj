package de.cobolj.runtime;

import com.oracle.truffle.api.interop.TruffleObject;

import de.cobolj.phrase.SizeOverflowException;

/**
 * Oberklasse für alle Picture-Ausprägungen. 
 * 
 * Ein Picture ist in Cobol die Typ-Definition für einen Speicherplatz.
 * 
 * @author flaechsig
 *
 */
public abstract class Picture implements TruffleObject {

	/** Maximale Anzahl von Stellen, die die Instanz der Klasse abbilden kann */
	protected final int size;

	/**
	 * @see {{@link #Pic9(short, boolean, long)}
	 */
	public Picture(int size) {
		this.size = size;
	}

	/**
	 * Setzt den Wert des Pictures mit dem übergebenen Wert. Entsprechend der
	 * Cobol-Regeln werden zu lange Werte entsprechend gekürzt.
	 * 
	 * @param object Zu setzender Wert.
	 */
	public abstract void setValue(Object object);
	
	/**
	 * Setzt den Wert eines Pictures auf den übergebenen Wert. Hierbei wird
	 * ein Größen-Check gemacht. D.h. der übergebene Wert muss auch in die
	 * Definition des Pictures passen. Es werden nicht die Cobol-Kürzungen 
	 * durchgeführt, sondern eine Excepion geworfen.
	 * 
	 * @param object zu setzendes Objekt.
	 * @param sizeCheck Kennzeichen, ob ein Größencheck durchgeführt wird.
	 * @throws SizeOverflowException Wird geworfen, wenn die Längenbegrenzung nicht eingehalten wird
	 */
	public abstract void setValue(Object object, boolean sizeCheck) throws SizeOverflowException;

	public abstract Object getValue();
}
