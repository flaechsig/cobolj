package de.cobolj.statement.perform;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;

import de.cobolj.CobolBaseTest;

/**
 * Testfälle für das PERFORM Statement
 * 
 * @author flaechsig
 *
 */
public class PerformTest extends CobolBaseTest {
	private static int ANZAHL_TESTFAELLE = 11;

	 /** 
	 *@return Liste aller Test-Objekte
	 */
	@DataProvider(name = "cobolTests")
	public Object[] acceptTest() {
		ArrayList<String> result = new ArrayList<>();
		for (int i = 1; i <= ANZAHL_TESTFAELLE; i++) {
			result.add("perform-" + i);
		}
		return result.toArray();
	}

}
