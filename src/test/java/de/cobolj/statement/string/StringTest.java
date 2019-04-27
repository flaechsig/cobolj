package de.cobolj.statement.string;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.DataProvider;

import de.cobolj.CobolBaseTest;

/**
 * Testfälle für das READ Statement
 * 
 * @author flaechsig
 *
 */
public class StringTest extends CobolBaseTest {
	private static int ANZAHL_TESTFAELLE = 1;

	 /** 
	 *@return Liste aller Test-Objekte
	 */
	@DataProvider(name = "cobolTests")
	public Object[] acceptTest() {
		ArrayList<String> result = new ArrayList<>();
		for (int i = 1; i <= ANZAHL_TESTFAELLE; i++) {
			result.add("String-" + i);
		}
		return result.toArray();
	}
}