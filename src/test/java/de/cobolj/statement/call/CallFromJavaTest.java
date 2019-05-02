package de.cobolj.statement.call;

import static de.cobolj.CobolExec.By.reference;
import static de.cobolj.CobolExec.By.value;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.cobolj.CobolExec;
import de.cobolj.CobolExec.By;;

public class CallFromJavaTest {

	private OutputStream out = null;

	@BeforeMethod
	public void before() {
		out = new ByteArrayOutputStream();
	}

	@Test
	public void testSimpleCall() throws Exception {
		CobolExec.register("/de/cobolj/statement/call/call-1.cob");
		CobolExec.call(System.in, out, "HELLO-WORLD");
		Assert.assertEquals(out.toString(), "Hello world!" + System.lineSeparator());
	}

	@Test
	public void testCallMitNumberParameter() throws Exception {
		CobolExec.register("/de/cobolj/statement/call/call-3.cob");
		Number name = 1234;
		CobolExec.call(System.in, out, "HELLO-WORLD", value(name));
		Assert.assertEquals(out.toString(), "Hello 1234" + System.lineSeparator());
	}

	@Test
	public void testCallMitNumber2Parameter() throws Exception {
		CobolExec.register("/de/cobolj/statement/call/call-3.cob");
		Number name = 12345;
		CobolExec.call(System.in, out, "HELLO-WORLD", value(name));
		Assert.assertEquals(out.toString(), "Hello 2345" + System.lineSeparator(),
				"Die Ausgabe muss abgeschnitten sein, da die Übergabe zu lang ist");
	}

	@Test
	public void testCallMitNumber3Parameter() throws Exception {
		CobolExec.register("/de/cobolj/statement/call/call-3.cob");
		Number name = new BigDecimal(125.12);
		CobolExec.call(System.in, out, "HELLO-WORLD", value(name));
		Assert.assertEquals(out.toString(), "Hello 0125" + System.lineSeparator(),
				"Nachkommastellen werden entfernt, da keine Dezimalzahl");
	}

	@Test
	public void testCallMitStringParameter() throws Exception {
		CobolExec.register("/de/cobolj/statement/call/call-2.cob");
		String name = "World";
		CobolExec.call(System.in, out, "HELLO-WORLD", value(name));
		Assert.assertEquals(out.toString(), "Hello World     " + System.lineSeparator());
	}

	@Test
	public void testCallMitString2Parameter() throws Exception {
		CobolExec.register("/de/cobolj/statement/call/call-2.cob");
		String name = "World 1234567";
		CobolExec.call(System.in, out, "HELLO-WORLD", value(name));
		Assert.assertEquals(out.toString(), "Hello World 1234" + System.lineSeparator(),
				"String ist abgeschnitten, da Eingabe zu lang");
	}

	@Test
	public void testCallMitMehrParametern() {
		CobolExec.register("/de/cobolj/statement/call/call-4.cob");
		String vorname = "Hans";
		String nachname = "Flächsig";
		Integer geburtsjahr = 1969;
		CobolExec.call(System.in, out, "HELLO-WORLD", value(vorname), value(nachname), value(geburtsjahr));
		Assert.assertEquals(out.toString(), "Hans      Flächsig  01969" + System.lineSeparator());
	}
	
	@Test
	public void testCallMitReferenceUndValue() {
		CobolExec.register("/de/cobolj/statement/call/call-6.cob");
		
		By val1 = value(1);
		By val2 = reference(1);
		
		CobolExec.call(System.in, out, "HELLO-WORLD", val1, val2);
		
		Assert.assertEquals(out.toString(), "01 01"+System.lineSeparator()+"02 02"+System.lineSeparator());
		Assert.assertEquals(val1.getValue(Integer.class), 1);
		Assert.assertEquals(val2.getValue(Integer.class), 2);
	}

	@Test
	public void testCobolProgramEndetNichtMitExitProgramm() throws Exception {
		try {
			CobolExec.register("/de/cobolj/statement/call/call-4.cob");
			CobolExec.call(System.in, out, "HELLO-WORLD");
			Assert.fail("Exception wegen fehlendes EXIT hätte kommen sollen");
		} catch (RuntimeException e) {

		}
	}

	@Test
	public void testSubprogrammUeberExcecuteIstVerboten() {
		Assert.fail();
//		Reader r = new InputStreamReader(this.getClass().getResourceAsStream("/de/cobolj/statement/call/call-1.cob"));
//		CobolExec.execute(r, System.in, System.out);
	}

}
