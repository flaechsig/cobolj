package de.cobolj.runtime;

import de.cobolj.phrase.SizeOverflowException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings("serial")
/*Fixme: Klasse Picture9V auflösen und komplete durch NubericPicture abbilden */
public abstract class NumericPicture extends Picture {

	/** Kennzeichen, ob die Instanz der Klasse ein Vorzeichen führt */
	protected final boolean signed;
	
	protected final char paddingChar;

	public NumericPicture(int level, String name, int size, boolean signed, boolean noPadding) {
		super(level, name, size);
		assert size <= 31 : "Der Parameter 'size' darf maximal 31 sein";
		this.signed = signed;
		this.paddingChar = noPadding?' ':'0';
	}
	
	public abstract BigDecimal getBigDecimal(); 
	
	public abstract int getScale();
	
	public abstract int getPrecession();

	@Override
	public void setValue(Object value) {
		BigDecimal tmpNumber = NumberUtils.createBigDecimal(value.toString());
		StringBuffer newVal = new StringBuffer();
		if(signed) {
			newVal.append((tmpNumber.compareTo(BigDecimal.ZERO) < 0 )?"-":"+");
		}
		tmpNumber = tmpNumber.abs();
		tmpNumber = tmpNumber.multiply(new BigDecimal(Math.pow(10,getScale())));
		tmpNumber = tmpNumber.setScale(0, RoundingMode.FLOOR);

		newVal.append(StringUtils.leftPad(StringUtils.right(tmpNumber.toString(), getPrecession()+getScale()), getPrecession()+getScale(), paddingChar));
		super.setValue(newVal.toString());
	}

	@Override
	public void setValue(Object object, boolean sizeCheck) throws SizeOverflowException {
		if (sizeCheck && object.toString().length() > getPrecession()+getScale()) {
			throw new SizeOverflowException();
		}
		setValue(object);
	}

	@Override
	public String getValue() {
		byte[] value = new byte[size];
		System.arraycopy(memory, memPointer, value, 0, size);
		return new String(value);
	}

	@Override
	public String toString() {
		String number = getValue();
		StringBuffer result = new StringBuffer();
		result.append(number.substring(0, (signed?1:0)+getPrecession()));
		if(getScale()>0) {
			result.append('.');
			result.append(number.substring((signed?1:0)+getPrecession()));
		}
		return result.toString();
	}
}
