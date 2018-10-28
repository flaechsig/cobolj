package de.cobolj.statement.compute;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;

import de.cobolj.CobolBaseTest;

/**
 * Testfälle für das COMPUTE Statement
 * 
 * @author flaechsig
 *
 */
public class ComputeTest extends CobolBaseTest {
	private static int ANZAHL_TESTFAELLE = 16;

	 /** 
	 *@return Liste aller Test-Objekte
	 */
	@DataProvider(name = "cobolTests")
	public Object[] acceptTest() {
		ArrayList<String> result = new ArrayList<>();
		for (int i = 1; i <= ANZAHL_TESTFAELLE; i++) {
			result.add("compute-" + i);
		}
		return result.toArray();
	}

}
