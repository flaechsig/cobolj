package de.cobolj.statement.read;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;

import de.cobolj.CobolBaseTest;

/**
 * Testfälle für das READ Statement
 * 
 * @author flaechsig
 *
 */
public class ReadTest extends CobolBaseTest {
	private static int ANZAHL_TESTFAELLE = 1;

	 /** 
	 *@return Liste aller Test-Objekte
	 */
	@DataProvider(name = "cobolTests")
	public Object[] acceptTest() {
		ArrayList<String> result = new ArrayList<>();
		for (int i = 1; i <= ANZAHL_TESTFAELLE; i++) {
			result.add("read-" + i);
		}
		return result.toArray();
	}
}
