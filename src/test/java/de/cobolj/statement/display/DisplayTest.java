package de.cobolj.statement.display;

import org.testng.annotations.DataProvider;

import de.cobolj.CobolBaseTest;

/**
 * Testfälle für das DISPLAY Statement
 * 
 * @author flaechsig
 *
 */
public class DisplayTest extends CobolBaseTest {

	/**
	 * @return Liste aller Test-Objekte
	 */
	@DataProvider(name = "cobolTests")
	public Object[] diplayTests() {
		return new Object[] { "display-1", "display-2", "display-3",};
	}
}
