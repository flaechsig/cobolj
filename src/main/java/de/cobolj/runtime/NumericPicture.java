package de.cobolj.runtime;

import java.math.BigDecimal;

@SuppressWarnings("serial")
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
	
	public abstract int getPrrecession();
	
}
