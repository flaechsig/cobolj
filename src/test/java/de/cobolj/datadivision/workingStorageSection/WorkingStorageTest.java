package de.cobolj.datadivision.workingStorageSection;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;

import de.cobolj.CobolBaseTest;

/**
 * Testfälle für die PictureGroup
 * 
 * @author flaechsig
 *
 */
public class WorkingStorageTest extends CobolBaseTest {
	
	private final int ANZAHLTESTS = 11;

	/**
	 * @return Liste aller Test-Objekte
	 */
	@DataProvider(name = "cobolTests")
	public Object[] tests() {
		ArrayList<String> testObjekte = new ArrayList<>();
		for(int i=1; i<= ANZAHLTESTS; i++) {
			testObjekte.add("ws-"+i);
		}
		return testObjekte.toArray();
	}
}
