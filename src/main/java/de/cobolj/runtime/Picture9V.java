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
import org.apache.commons.lang3.math.NumberUtils;

@SuppressWarnings("serial")
@MessageResolution(receiverType = Picture9V.class)
public class Picture9V extends NumericPicture implements Comparable<Picture9V> {

	/** Anzahl der Stellen vor dem Komma */
	private final int precession;
	/** Anzahl der Stellen nach dem Komma */
	private final int scale;

	public Picture9V(int level, String name, int precession, int scale, boolean signed, boolean noPadding) {
		super(level, name, precession+(signed?1:0)+scale, signed, noPadding);
		this.precession = precession;
		this.scale = scale;
	}

	@Override
	public ForeignAccess getForeignAccess() {
		return Picture9VForeign.ACCESS;
	}

	@Override
	public int compareTo(Picture9V o) {
		return getBigDecimal().compareTo(o.getBigDecimal());
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
	public BigDecimal getBigDecimal() {
		return NumberUtils.createBigDecimal(getValue());
	}

	@Override
	public int getScale() {
		return this.scale;
	}

	@Override
	public int getPrecession() {
		return this.precession;
	}

	@Override
	public void clear() {
		setValue(BigDecimal.ZERO);
	}

}
