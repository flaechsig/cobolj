package de.cobolj.nodes;

import org.apache.commons.lang3.StringUtils;

import de.cobolj.runtime.Picture;
import de.cobolj.runtime.Picture9V;
import de.cobolj.runtime.PictureA;

public class PictureFactory {
	private PictureFactory() {	}
	
	/**
	 * Erzeugt ein Node anhand des Picture-Strings
	 * 
	 * @param picture Langform des Picture-Strings
	 * @return Picture-Node des entsprechden Typs
	 */
	public static Picture create(String picture) {
		// Folgende Zeichen dürfen maximal einmal vorhanden sein
		assert StringUtils.countMatches(picture, "S") <= 1 : "S darf maximal einmal im Picture-String auftreten";
		assert StringUtils.countMatches(picture, "V") <= 1 : "V darf maximal einmal im Picture-String auftreten";
		assert StringUtils.countMatches(picture, ".") <= 1 : ". darf maximal einmal im Picture-String auftreten";
		assert StringUtils.countMatches(picture, "CR") <= 1 : "CR darf maximal einmal im Picture-String auftreten";
		assert StringUtils.countMatches(picture, "DB") <= 1 : "DB darf maximal einmal im Picture-String auftreten";
		
		if(StringUtils.containsOnly(picture, 'A')) {
			return createAlphabeticPicuture(picture);
		} else if(StringUtils.containsOnly(picture, '9', 'P', 'S', 'V')) {
			return createNumericPicture(picture);
		} else if(StringUtils.containsOnly(picture, '9', 'A', 'X')) {
			return createAlphNumericPicuture(picture);
		} else {
			throw new RuntimeException("Not implemented");
		}
	}

	private static Picture createAlphabeticPicuture(String picture) {
		assert picture.length() < 256; // Maximale Lännge dieses Datentyps
		return new PictureA(picture.length());
	}
	
	private static Picture createAlphNumericPicuture(String picture) {
		assert picture.length() < 256; // Maximale Lännge dieses Datentyps
		return new PictureA(picture.length());
	}

	private static Picture createNumericPicture(String picture) {
		assert StringUtils.countMatches(picture, '9') <=18 : "Maximale Länge 18"; 
		
		boolean sign = StringUtils.contains(picture, 'S');
		int precission = StringUtils.countMatches(picture, '9');
		int scale = 0;
		if(StringUtils.contains(picture, 'V')) {
			int index = picture.indexOf('V');
			scale = StringUtils.countMatches(picture.substring(index), '9');
			precission = precission-scale;
		}
		
		return new Picture9V(precission, scale, sign);
	}
}
