package de.cobolj.parser;

import java.math.BigDecimal;

import de.cobolj.nodes.CobolNode;

/**
 * Repräsentiert die möglichen ADD-Implementierungen
 * 
 * @author flaechsig
 *
 */
public abstract class AddImplNode extends CobolNode {
	/** Kennzeichen, ob SIZE-ERRORs behandelt werden */
	protected boolean sizeErrorCheck;

	public void setSizeErrorCheck(boolean sizeErrorCheck) {
		this.sizeErrorCheck = sizeErrorCheck;
	}
	
	/**
	 *  Implementiert die Unterschiede für ADD TO und ADD GIVING
	 * 
	 * @param sum
	 * @param value
	 * @return
	 */
	protected abstract BigDecimal calculateNewValue(BigDecimal sum, BigDecimal value);
}
