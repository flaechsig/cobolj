package de.cobolj.runtime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.MessageResolution;
import com.oracle.truffle.api.interop.TruffleObject;

import de.cobolj.phrase.SizeOverflowException;

@MessageResolution(receiverType = Picture9V.class)
public class Picture9V extends NumericPicture implements Comparable<Picture9V> {

	/** Kommazahl wird hier durch einen BigDecimal abgebildet. */
	private BigDecimal value;
	/** Kennzeichen, ob es sich um eine Vorzeichen behaftete Zahl handelt. */
	private final boolean signed;
	/** Anzahl der Stellen vor dem Komma */
	private final int precession;
	/** Anzahl der Stellen nach dem Komma */
	private final int scale;

	public Picture9V(String name, int precession, int scale, boolean signed, PictureGroup parent) {
		super(name, precession, parent);
		this.precession = precession;
		this.signed = signed;
		this.scale = scale;
		value = BigDecimal.ZERO;
	}

	@Override
	public ForeignAccess getForeignAccess() {
		return Picture9VForeign.ACCESS;
	}

	@Override
	public void setValue(Object object) {
		String obj = object.toString();
		String ganzzahl = obj;
		String nachkomma = "";
		boolean sign = false;
		if ("-".equals(obj.substring(0, 1))) {
			sign = true;
			ganzzahl = ganzzahl.substring(1);
		}
		int idxDecimalPoint = obj.indexOf('.');
		if (idxDecimalPoint > -1) {
			ganzzahl = obj.substring(0, idxDecimalPoint);
			nachkomma = obj.substring(idxDecimalPoint + 1);
		}
		ganzzahl = StringUtils.right(ganzzahl, precession);
		setValue(new BigDecimal((sign==false?"":"-")+ganzzahl + "." + nachkomma));
	}

	public void setValue(BigDecimal object) {
		value = object.setScale(scale, RoundingMode.FLOOR);
	}

	@Override
	public void setValue(Object object, boolean sizeCheck) throws SizeOverflowException {
		BigDecimal decimal = new BigDecimal(object.toString());
		if (sizeCheck && decimal.precision() > size) {
			throw new SizeOverflowException();
		}
		setValue(object);
	}

	@Override
	public int compareTo(Picture9V o) {
		return value.compareTo(o.value);
	}

	/**
	 * Prüft, ob das übergebene Object ebenfalls vom Typ Picture9V ist
	 * 
	 * @param obj Zu prüfendes Objekt
	 * @return true, wenn das übergebene Objekt auch Pic9 ist.
	 */
	static boolean isInstance(TruffleObject obj) {
		return obj instanceof Picture9V;
	}

	@Override
	public String toString() {
		String sign = "";
		if (this.signed) {
			sign = value.floatValue() < 0 ? "-" : "+";
		}
		DecimalFormat df = new DecimalFormat();
		df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
		df.setGroupingUsed(false);
		df.setMinimumFractionDigits(scale);
		df.setMaximumFractionDigits(scale);
		df.setFormatWidth(precession + scale + (scale > 0 ? 1 : 0)); // Komma berücksichtigen
		df.setPadCharacter('0');
		return sign + df.format(value.abs());
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public BigDecimal getBigDecimal() {
		return value;
	}

	@Override
	public int getScale() {
		return this.scale;
	}

	@Override
	public int getPrrecession() {
		return this.precession;
	}

	@Override
	public void clear() {
		this.value = BigDecimal.ZERO;
	}

}
