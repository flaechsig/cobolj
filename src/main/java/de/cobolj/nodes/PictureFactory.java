package de.cobolj.nodes;

import org.apache.commons.lang3.StringUtils;

import de.cobolj.runtime.Picture;
import de.cobolj.runtime.Picture9V;
import de.cobolj.runtime.PictureA;
import de.cobolj.runtime.PictureGroup;

public class PictureFactory {
	private PictureFactory() {	}
	
	/**
	 * Erzeugt ein Node anhand des Picture-Strings
	 * 
	 * @param picture Langform des Picture-Strings
	 * @return Picture-Node des entsprechden Typs
	 */
	public static Picture create(String name, String picture, PictureGroup parent) {
		// Folgende Zeichen dürfen maximal einmal vorhanden sein
		assert StringUtils.countMatches(picture, "S") <= 1 : "S darf maximal einmal im Picture-String auftreten";
		assert StringUtils.countMatches(picture, "V") <= 1 : "V darf maximal einmal im Picture-String auftreten";
		assert StringUtils.countMatches(picture, ".") <= 1 : ". darf maximal einmal im Picture-String auftreten";
		assert StringUtils.countMatches(picture, "CR") <= 1 : "CR darf maximal einmal im Picture-String auftreten";
		assert StringUtils.countMatches(picture, "DB") <= 1 : "DB darf maximal einmal im Picture-String auftreten";
		
		if(StringUtils.containsOnly(picture, 'A')) {
			return createAlphabeticPicuture(name, picture, parent);
		} else if(StringUtils.containsOnly(picture, '9', 'P', 'S', 'V')) {
			return createNumericPicture(name, picture, parent);
		} else if(StringUtils.containsOnly(picture, '9', 'A', 'X')) {
			return createAlphNumericPicuture(name, picture, parent);
		} else {
			throw new RuntimeException("Not implemented");
		}
	}

	private static Picture createAlphabeticPicuture(String name, String picture, PictureGroup parent) {
		assert picture.length() < 256; // Maximale Lännge dieses Datentyps
		return new PictureA(name, picture.length(), parent);
	}
	
	private static Picture createAlphNumericPicuture(String name, String picture, PictureGroup parent) {
		assert picture.length() < 256; // Maximale Lännge dieses Datentyps
		return new PictureA(name, picture.length(), parent);
	}

	private static Picture createNumericPicture(String name, String picture, PictureGroup parent) {
		assert StringUtils.countMatches(picture, '9') <=18 : "Maximale Länge 18"; 
		
		boolean sign = StringUtils.contains(picture, 'S');
		int precission = StringUtils.countMatches(picture, '9');
		int scale = 0;
		if(StringUtils.contains(picture, 'V')) {
			int index = picture.indexOf('V');
			scale = StringUtils.countMatches(picture.substring(index), '9');
			precission = precission-scale;
		}
		
		return new Picture9V(name, precission, scale, sign, parent);
	}
}
