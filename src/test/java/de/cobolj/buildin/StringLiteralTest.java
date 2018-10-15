package de.cobolj.buildin;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import de.cobolj.util.StringLiteral;

public class StringLiteralTest {
	
	@Test(dataProvider = "positive")
	public void testSimpleLiteralPositiv(String input, String output) {

		Assert.assertEquals(new StringLiteral(input).toString(),output);
	}
	
	@Test(dataProvider = "negativ")
	public void testSimpleLiteralFail(String input, String output) {
		try {
			new StringLiteral(input).toString();
			Assert.fail();
		} catch(AssertionError e)  {
			// OK
		};
	}

	@DataProvider(name = "positive")
	public static Object[][] providerPositiv() {
		return new Object[][] { 
			{ "\"\"", "" }, 
			{ "\"Hello World\"", "Hello World" },
			{ "'Hello World'", "Hello World" },
			{ "''''", "'" },
			{ "\"\"\"\"", "\"" },
		};
	}

	@DataProvider(name = "negativ")
	public static Object[][] providerNegativ() {
		return new Object[][] { 
			{ "A", "" }, 
			{ null, "" },
		};
	}
}
