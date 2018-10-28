package de.cobolj.statement.ifstmt;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;

import de.cobolj.CobolBaseTest;

/**
 * Testfälle für das IF Statement
 * 
 * @author flaechsig
 *
 */
public class IfTest extends CobolBaseTest {
	private static int ANZAHL_TESTFAELLE = 2;

	 /** 
	 *@return Liste aller Test-Objekte
	 */
	@DataProvider(name = "cobolTests")
	public Object[] acceptTest() {
		ArrayList<String> result = new ArrayList<>();
		for (int i = 1; i <= ANZAHL_TESTFAELLE; i++) {
			result.add("if-" + i);
		}
		return result.toArray();
	}

}
