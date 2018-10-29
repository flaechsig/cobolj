package de.cobolj.runtime;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.MessageResolution;
import com.oracle.truffle.api.interop.TruffleObject;

import de.cobolj.phrase.SizeOverflowException;

/**
 * Cobol kennt nur wenige Datentypen. Pic 9 (bzw. Picture 9) bildet numerische
 * Werte ab. Dabei handelt es sich in dieser Abbildung sowohl um vorzeichen
 * behaftete, wie auch nicht vorzeichen behaftete Werte. In Cobol bilden sich
 * diese durch die Definition
 * 
 * <ul>
 * <li>name PIC 9(...)</li>
 * <li>name PIC S9(...)</li>
 * </ul>
 * 
 * ab.
 * 
 * @author flaechsig
 *
 */
@MessageResolution(receiverType = Picture9.class)
public class Picture9 extends NumericPicture implements Comparable<Picture9> {

	/** Kennzeichen, ob die Instanz der Klasse ein Vorzeichen führt */
	private final boolean signed;
	/** Aktueller Wert, der durch die Klasse abgebildet wird */
	private long value = 0;

	/**
	 * @see {{@link #Pic9(short, boolean, long)}
	 */
	public Picture9(int size) {
		this(size, false, 0);
	}

	/**
	 * @see {{@link #Pic9(short, boolean, long)}
	 */
	public Picture9(int size, boolean signed) {
		this(size, signed, 0);
	}

	/**
	 * Legt ein Picture mit einer maximalen definierten Länge an. Standarmäßig wird
	 * in diesem Fall von einem nicht vorzeichen behafteten Wert ausgegangen.
	 * 
	 * @param size   maximale Anzahl Stellen, die durch eine Instanz der Klasse
	 *               abgebildet werden können. In Cobol ist eine maximale Länge von
	 *               31 Stellen definiert.
	 * @param signed Kennzeichen, ob diese Instanz ein Vorzeichen mit sich führt
	 * @param value  Initialer Wert dieser Instanz
	 */
	public Picture9(int size, boolean signed, long value) {
		super(size);
		assert size <= 31 : "Der Parameter 'size' darf maximal 31 sein";
		this.signed = signed;
		setValue(value);
	}

	/**
	 * Setter für den Wert des Pic9. Cobol hat hierbei einen sehr robusten Umgang
	 * mit den übergebenen Werten. Wenn die maximal erlaubte Länge überschritten
	 * wird, dann werden die überzähligen linken positionen nicht übernommen. Wenn
	 * das Value entgegen der Vorgabe ein Vorzeichen hat, dann wird dies ggf.
	 * einfach ignoriert.
	 * 
	 * @param value zu setzender Wert für die Instanz.
	 */
	public void setValue(long value) {
		int mul;
		if (!signed) {
			value = Math.abs(value);
		}

		mul = value < 0 ? -1 : 1;
		String tmpValue = String.valueOf(Math.abs(value));
		int oversize = tmpValue.length() - size;
		value = Long.parseLong(tmpValue.substring(Math.max(0, oversize)));

		this.value = mul * value;
	}

	@Override
	public void setValue(Object object) {
		setValue(new BigDecimal(object.toString()).longValue());
	}
	
	@Override
	public void setValue(Object object, boolean sizeCheck) throws SizeOverflowException {
		if(sizeCheck && object.toString().length() > size) {
			throw new SizeOverflowException();
		}
		setValue(object);
	}

	@Override
	public int compareTo(Picture9 o) {
		if (value < o.value) {
			return -1;
		} else if (value == o.value) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public ForeignAccess getForeignAccess() {
		return Picture9Foreign.ACCESS;
	}

	/**
	 * Prüft, ob das übergebene Object ebenfalls vom Typ Pic9 ist
	 * 
	 * @param obj Zu prüfendes Objekt
	 * @return true, wenn das übergebene Objekt auch Pic9 ist.
	 */
	static boolean isInstance(TruffleObject obj) {
		return obj instanceof Picture9;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		if (signed) {
			buf.append(value < 0 ? "-" : "+");
		}
		buf.append(StringUtils.leftPad(String.valueOf(Math.abs(value)), size, '0'));
		return buf.toString();
	}

	@Override
	public Object getValue() {
		return BigDecimal.valueOf(value);
	}

	@Override
	public BigDecimal getBigDecimal() {
		return BigDecimal.valueOf(value);
	}

	@Override
	public int getScale() {
		return 0;
	}

	@Override
	public int getPrrecession() {
		return size;
	}

	@Override
	public void clear() {
		this.value = 0;
	}

}
