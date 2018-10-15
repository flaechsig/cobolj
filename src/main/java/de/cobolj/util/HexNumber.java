package de.cobolj.util;

import java.util.ArrayList;

/**
 * Abbildung eines Cobol-Hex-Strings. In Cobol besteht aus einem oder mehreren
 * Hex-Werten. Ein einzelne Hex-Werte bestehen dabei immer aus zwei Zeichen. Der
 * Hex-String wird ein vorangestelltes X eingeleitet.
 * 
 * @author flaechsig
 *
 */
public class HexNumber {
	private ArrayList<Character> valueArray = new ArrayList<>();
	
	public HexNumber(String cobolHexNubmer) {
		assert cobolHexNubmer.length() % 2 == 1 : "Die cobolHexNumber muss eine gerade Anzahl Zeichen haben";
		
		String value = cobolHexNubmer.substring(2, cobolHexNubmer.length()-1);
		int length = value.length() / 2;
		
		for(int i=0; i<=length; i+=2) {
			String hexvalue = value.substring(i, i+2);
			valueArray.add((char)Integer.parseInt(hexvalue, 16));
		}
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for(Character c : valueArray) {
			buf.append(c);
		}
		return buf.toString();
	}
}
