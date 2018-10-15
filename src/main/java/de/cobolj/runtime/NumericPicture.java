package de.cobolj.runtime;

import java.math.BigDecimal;

public abstract class NumericPicture extends Picture {

	public NumericPicture(int size) {
		super(size);
	}
	
	public abstract BigDecimal getBigDecimal(); 
	
	public abstract int getScale();
	
	public abstract int getPrrecession();

}
