package de.cobolj.statement.movecorr;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;

import de.cobolj.CobolBaseTest;

/**
 * Testfälle für das MOVE Statement
 * 
 * @author flaechsig
 *
 */
public class MoveCorrTest extends CobolBaseTest {
	private static int ANZAHL_TESTFAELLE = 1;

	 /** 
	 *@return Liste aller Test-Objekte
	 */
	@DataProvider(name = "cobolTests")
	public Object[] acceptTest() {
		ArrayList<String> result = new ArrayList<>();
		for (int i = 1; i <= ANZAHL_TESTFAELLE; i++) {
			result.add("move-corr-" + i);
		}
		return result.toArray();
	}

}