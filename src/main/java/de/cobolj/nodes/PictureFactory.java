package de.cobolj.nodes;

import org.apache.commons.lang3.StringUtils;

import de.cobolj.runtime.Picture;
import de.cobolj.runtime.Picture9V;
import de.cobolj.runtime.PictureA;
import de.cobolj.runtime.PictureGroup;
import de.cobolj.runtime.PictureX;

public class PictureFactory {
	private PictureFactory() {
	}

	/**
	 * Erzeugt ein Node anhand des Picture-Strings
	 * 
	 * @param picture Langform des Picture-Strings
	 * @return Picture-Node des entsprechden Typs
	 */
	public static Picture create(int level, String name, String picture) {
		// Folgende Zeichen dürfen maximal einmal vorhanden sein
		assert StringUtils.countMatches(picture, "S") <= 1 : "S darf maximal einmal im Picture-String auftreten";
		assert StringUtils.countMatches(picture, "V") <= 1 : "V darf maximal einmal im Picture-String auftreten";
		assert StringUtils.countMatches(picture, ".") <= 1 : ". darf maximal einmal im Picture-String auftreten";
		assert StringUtils.countMatches(picture, "CR") <= 1 : "CR darf maximal einmal im Picture-String auftreten";
		assert StringUtils.countMatches(picture, "DB") <= 1 : "DB darf maximal einmal im Picture-String auftreten";
		
		picture = picture.toUpperCase();
		if (picture == null) {
			return new PictureGroup(level, name);
		} else if (StringUtils.containsOnly(picture, 'A')) {
			return createAlphabeticPicuture(level, name, picture);
		} else if (StringUtils.containsOnly(picture, '9', 'P', 'S', 'V', 'Z')) {
			return createNumericPicture(level, name, picture);
		} else if (StringUtils.containsOnly(picture, '9', 'A', 'X', 'B')) {
			return createAlphNumericPicuture(level, name, picture);
		} else {
			throw new RuntimeException("Not implemented");
		}
	}

	private static Picture createAlphabeticPicuture(int level, String name, String picture) {
		assert picture.length() < 256; // Maximale Lännge dieses Datentyps
		return new PictureA(level, name, picture.length());
	}

	private static Picture createAlphNumericPicuture(int level, String name, String picture) {
		assert picture.length() < 256; // Maximale Lännge dieses Datentyps
		return new PictureX(level, name, picture);
	}

	private static Picture createNumericPicture(int level, String name, String picture) {
		assert StringUtils.countMatches(picture, '9') <= 18 : "Maximale Länge 18";

		boolean sign = StringUtils.contains(picture, 'S');
		boolean noPadding = StringUtils.contains(picture, "Z");
		int precission = StringUtils.countMatches(picture, '9');
		precission += StringUtils.countMatches(picture, 'Z');
		int scale = 0;
		if (StringUtils.contains(picture, 'V')) {
			int index = picture.indexOf('V');
			scale = StringUtils.countMatches(picture.substring(index), '9');
			precission = precission - scale;
		}

		return new Picture9V(level, name, precission, scale, sign, noPadding);
	}
}
