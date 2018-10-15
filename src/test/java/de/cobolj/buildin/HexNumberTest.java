package de.cobolj.buildin;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import de.cobolj.util.HexNumber;

public class HexNumberTest {

	@Test(dataProvider = "positive")
	public void testHexNumLiteralPositiv(String input, String output) {
		Assert.assertEquals(new HexNumber(input).toString(), output);
	}

	@Test(dataProvider = "negativ")
	public void testHexNumLiteralNegativ(String input) {
		try {
			new HexNumber(input);
			Assert.fail();
		} catch (AssertionError e) {
			// So soll es sein :-)
		}
	}

	@DataProvider(name="positive") 
	public Object[][] providerPositiv()  {
		return new Object[][]  {
			{"x'4040'", "@@"},
			{"X'4040'", "@@"},
			{"x\"4040\"", "@@"},
			{"X\"4040\"", "@@"},
		};
	}

	@DataProvider(name="negativ") 
	public Object[] providerNegativ()  {
		return new Object[]  {
				"x\"404\"", // Keine ungerade Eingabe
		};
	}
}
