package de.cobolj.statement.continuestmt;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;

import de.cobolj.CobolBaseTest;

/**
 * Testfälle für das CONTINUE Statement
 * 
 * @author flaechsig
 *
 */
public class ContinueTest extends CobolBaseTest {
	private static int ANZAHL_TESTFAELLE = 1;

	 /** 
	 *@return Liste aller Test-Objekte
	 */
	@DataProvider(name = "cobolTests")
	public Object[] acceptTest() {
		ArrayList<String> result = new ArrayList<>();
		for (int i = 1; i <= ANZAHL_TESTFAELLE; i++) {
			result.add("continue-" + i);
		}
		return result.toArray();
	}
}
