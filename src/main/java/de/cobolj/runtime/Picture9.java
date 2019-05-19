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
@SuppressWarnings("serial")
@MessageResolution(receiverType = Picture9.class)
public class Picture9 extends NumericPicture implements Comparable<Picture9> {

	/** Aktueller Wert, der durch die Klasse abgebildet wird */
//	private long value = 0;

	/**
	 * @see {{@link #Pic9(short, boolean, long)}
	 */
	public Picture9(int level, String name, int size ) {
		this(level, name, size, false);
	}

	/**
	 * @see {{@link #Pic9(short, boolean, long)}
	 */
	public Picture9(int level, String name, int size, boolean signed ) {
		this(level, name, size, signed, 0, false);
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
	public Picture9(int level, String name, int size, boolean signed, long value, boolean noPadding) {
		super(level, name, size, signed, noPadding);
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
	public void setValue(int idx, long value) {
		int mul;
		if (!signed) {
			value = Math.abs(value);
		}

		mul = value < 0 ? -1 : 1;
		String tmpValue = String.valueOf(Math.abs(value));
		int oversize = tmpValue.length() - size;
		value = Long.parseLong(tmpValue.substring(Math.max(0, oversize)));

		byte[] memValue =StringUtils.leftPad(""+(mul * value), size, ' ').getBytes();
		System.arraycopy(memValue, 0, memory, memPointer, size);
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
		return getBigDecimal().compareTo(o.getBigDecimal());
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
		long value = getBigDecimal().longValue();
		StringBuffer buf = new StringBuffer();
		if (signed) {
			buf.append(value < 0 ? "-" : "+");
		}
		buf.append(StringUtils.leftPad(String.valueOf(Math.abs(value)), size, paddingChar));
		return buf.toString();
	}

	@Override
	public Object getValue() {
		byte[] value = new byte[size];
		System.arraycopy(memory, memPointer, value, 0, size);
		return new BigDecimal(new String(value));
	}

	@Override
	public BigDecimal getBigDecimal() {
		return (BigDecimal) getValue();
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
		setValue(0);
	}

}
