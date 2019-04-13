package de.cobolj.runtime;

/**
 * Verwaltung eines einzelnen Speicherbereichs (z.B. Working-Storage). Über den
 * Storage kann auf unterschiedliche Zugriffswege auf einzelne Datenbereiche
 * zugegriffen werden. Hierbei werden verschiedene Möglichkeiten des Zugriffs
 * geboten, um den unterschiedlichen Anfroderungen Rechnung zu tragen.
 * 
 * @author flaechsig
 *
 */
public class Storage {
	
	/** Fügt dem Storage ein Picture hinzu.
	 * 
	 * @param name Name des Pictures
	 * @param picture das Picture selbst
	 * @param parent Elternteil des Pictures
	 */
	public void addPicture(String name, Picture picture, Picture parent) {

	}
	
	/** 
	 * @return Liefert das erste Picture anhand des Namens des Pictures.
	 * 
	 * @param name Name nach dem gesucht wird
	 * 
	 */
	public Picture get(String name) {
		return null;
	}
	
	/** 
	 * 
	 * @param name Name des Pictures
	 * @param of Elternelement des gesuchten Pictures (dies kann auch in einer höheren Ebene vorhanden sein)
	 * @return Liefert das erste Picture, das Element des Pictures 'of' ist
	 */
	public Picture get(String name, String of ) {
		return null;
	}
	
	public Picture get(String name, Picture of) {
		return null;
	}
}
