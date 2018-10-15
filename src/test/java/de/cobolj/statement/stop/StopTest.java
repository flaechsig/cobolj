package de.cobolj.statement.stop;

import org.testng.annotations.DataProvider;

import de.cobolj.CobolBaseTest;

/**
 * Testfälle für das STOP RUN Statement
 * 
 * @author flaechsig
 *
 */
public class StopTest extends CobolBaseTest {

	/**
	 * @return Liste aller Test-Objekte
	 */
	@DataProvider(name = "cobolTests")
	public Object[] diplayTests() {
		return new Object[] { "stop-1", "stop-2"};
	}
}
