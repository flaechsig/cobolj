package de.cobolj.runtime;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

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
@SuppressWarnings("serial")
public abstract class Picture implements TruffleObject, Serializable {
	/** Konstante für die Markierung eines Fillers. */
	public final static String FILLER = "FILLER";

	/** Übergeodnete PictureGroup */
	protected PictureGroup parent;
	/** Level-Nummer des Picture */
	protected final int level;
	/** Name des Pictures */
	protected final String name;
	/** Maximale Anzahl von Stellen, die die Instanz der Klasse abbilden kann */
	protected final int size;
	/** Subscript, wenn denn eins existiert */
	protected Integer subscript;

	/**
	 * @see {{@link #Pic9(short, boolean, long)}
	 */
	public Picture(int level, String name, int size) {
		this(level, name, size, null);
	}

	/**
	 * @see {{@link #Pic9(short, boolean, long)}
	 */
	public Picture(int level, String name, int size, Integer subscript) {
		this.level = level;
		this.name = name;
		this.size = size;
		this.subscript = subscript;
	}

	/**
	 * Setzt den Wert des Pictures mit dem übergebenen Wert. Entsprechend der
	 * Cobol-Regeln werden zu lange Werte entsprechend gekürzt.
	 * 
	 * @param object Zu setzender Wert.
	 */
	public abstract void setValue(Object object);

	/**
	 * Setzt den Wert eines Pictures auf den übergebenen Wert. Hierbei wird ein
	 * Größen-Check gemacht. D.h. der übergebene Wert muss auch in die Definition
	 * des Pictures passen. Es werden nicht die Cobol-Kürzungen durchgeführt,
	 * sondern eine Excepion geworfen.
	 * 
	 * @param object    zu setzendes Objekt.
	 * @param sizeCheck Kennzeichen, ob ein Größencheck durchgeführt wird.
	 * @throws SizeOverflowException Wird geworfen, wenn die Längenbegrenzung nicht
	 *                               eingehalten wird
	 */
	public abstract void setValue(Object object, boolean sizeCheck) throws SizeOverflowException;

	public abstract Object getValue();

	/**
	 * löscht den Inhalt eines Pictures.
	 */
	public abstract void clear();

	/** Liest aus einem InputStream die Daten für das Picture */
	public int parse(InputStream is) {
		int result = -1;
		byte[] buffer = new byte[size];
		try {
			result = is.read(buffer);
			if (result > -1) {
				setValue(new String(buffer));
			}
		} catch (IOException e) {
			// Nothing to do
		}

		return result;
	}

	/** Liefert den symbolischen Namen des Pictures */
	public String getName() {
		return name;
	}

	public PictureGroup getParent() {
		return parent;
	}

	public int getSize() {
		return size;
	}

	public boolean isFiller() {
		return FILLER.equals(name);
	}

	public String getQualifiedName() {
		if (parent == null) {
			return name;
		} else {
			return name + " OF " + parent.getQualifiedName();
		}
	}

	public String getSubscript() {
		if (subscript == null && getParent() == null) {
			return "";
		} else if (subscript == null && getParent() != null) {
			return getParent().getSubscript();
		} else if (subscript != null && getParent() == null) {
			return "(" + subscript + ")";
		} else /* subscribe != null & parent != null */ {
			return "(" + (getParent().getSubscript() + " " + subscript).trim() + ")";
		}
	}

	public void setSubscript(int i) {
		this.subscript = i;
	}

	public void setParent(PictureGroup parent) {
		this.parent = parent;
		parent.add(this.name, this);
	}

	public int getLevel() {
			return this.level;
	}
}
