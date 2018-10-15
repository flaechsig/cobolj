package de.cobolj.util;

/**
 * Bildet ein Cobol String-Literal ab. In Cobol ist ein String eine
 * Zeichenkette, die durch ' oder " begrenzt wird.
 * 
 * @author flaechsig
 *
 */
public class StringLiteral {
	private String value;

	/**
	 * Construktor. Formt den Cobol-String in eine Java-String-Repräsentation um
	 * 
	 * @param cobolLiteral Vollständiges Cobol String-Literal
	 */
	public StringLiteral(String cobolLiteral) {
		assert cobolLiteral != null : "cobolLiteral dar nicht null sein";
		assert cobolLiteral.length()>1 : "cobolLiteral muss mindestens zwei Zeichen haben";
		
		String firstChar = cobolLiteral.substring(0,1);
		this.value = cobolLiteral.substring(1, cobolLiteral.length() - 1);
		this.value = this.value.replaceAll(firstChar+firstChar, firstChar);
	}

	public String toString() {
		return this.value;
	}
}
