package de.cobolj;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Basis Test-Klassse für die Cobol-Ausführung. Jeder Testfall ist in drei
 * Dateien beschrieben. Dem Cobol-Programm, dem geplanten Ergebnis und einer
 * Fehlerdatei, die einen Hinweis auf den Testfall enthält.
 * 
 * @author flaechsig
 *
 */
public abstract class CobolBaseTest {

	/** 
	 * In Abhängigkeit des übergebenen Input-Namens werden drei testrelevante
	 * Dateien geladen und die Coboldatei wird ausgeführt. Das Ergebnis wird mit dem 
	 * erwarteten Ergebnis verglichen. Ggf. wird der Text der Error-Datei ausgeben,
	 * um dem Benutzer eine spezifischere Information zu liefern
	 * 
	 * @param input Name des Testfall-Tripels
	 */
	@Test(dataProvider = "cobolTests")
	public void cobolTest(String input) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			// Input File
			InputStream fileStream = this.getClass().getResourceAsStream(input + ".cob");
			InputStreamReader fileReader = new InputStreamReader(fileStream);

			// Expected Result
			InputStream expectedStream = this.getClass().getResourceAsStream(input + ".out");
			String expected = IOUtils.toString(expectedStream, Charset.defaultCharset());

			// Error-Message
			InputStream errorStream = this.getClass().getResourceAsStream(input + ".err");
			String error = IOUtils.toString(errorStream, Charset.defaultCharset());
			
			InputStream inputStream = this.getClass().getResourceAsStream(input + ".in");
			if(inputStream == null) {
				inputStream = System.in;
			}

			CobolExec.execute(fileReader, inputStream, out);
			Assert.assertEquals(out.toString(), expected, input+ ": "+error);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
