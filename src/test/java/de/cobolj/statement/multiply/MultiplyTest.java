package de.cobolj.statement.multiply;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;

import de.cobolj.CobolBaseTest;

/**
 * Testfälle für das ADD Statement
 * 
 * @author flaechsig
 *
 */
public class MultiplyTest extends CobolBaseTest {
	private static int ANZAHL_TESTFAELLE = 2;

	 /** 
	 *@return Liste aller Test-Objekte
	 */
	@DataProvider(name = "cobolTests")
	public Object[] acceptTest() {
		ArrayList<String> result = new ArrayList<>();
		for (int i = 1; i <= ANZAHL_TESTFAELLE; i++) {
			result.add("multiply-" + i);
		}
		return result.toArray();
	}
}
