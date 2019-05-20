package de.cobolj;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
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
	public void cobolTest(List<String> input) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			int idx = this.getClass().getName().lastIndexOf('.');
			String packageName = this.getClass().getName().substring(0, idx);
			packageName = packageName.replace('.', '/');
			for (String i : input) {
				CobolExec.register("/" + packageName + "/" + i + ".cob");
			}

			// Input File
			InputStream fileStream = this.getClass().getResourceAsStream(input.get(0) + ".cob");
			InputStreamReader fileReader = new InputStreamReader(fileStream);

			// Expected Result
			InputStream expectedStream = this.getClass().getResourceAsStream(input.get(0) + ".out");
			String expected = IOUtils.toString(expectedStream, Charset.defaultCharset());

			// Error-Message
			InputStream errorStream = this.getClass().getResourceAsStream(input.get(0) + ".err");
			String error = IOUtils.toString(errorStream, Charset.defaultCharset());

			InputStream inputStream = this.getClass().getResourceAsStream(input.get(0) + ".in");
			if (inputStream == null) {
				inputStream = System.in;
			}

			CobolExec.execute(fileReader, inputStream, out);
			Assert.assertEquals(out.toString(), expected, input.get(0) + ": " + error);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * @return Liste aller Test-Objekte
	 */
	@DataProvider(name = "cobolTests")
	public Object[] acceptTest() {
		Collection<List<String>> result;
		String packageName = this.getClass().getName();
		int idx = packageName.lastIndexOf('.');
		packageName = packageName.substring(0, idx);
		packageName = packageName.replace('.', '/');
		String startWith = this.getClass().getSimpleName();
		startWith = startWith.substring(0, startWith.length()-4).toLowerCase();

		result = getResourceFiles(packageName, startWith);
		return result.toArray();
	}

	private Collection<List<String>> getResourceFiles(String path, String startWith) {
		Set<String> allFilenames = new TreeSet<>();
		Collection<List<String>> result = new ArrayList<>();

		try (InputStream in = getResourceAsStream(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String resource;

			while ((resource = br.readLine()) != null) {
				if (!resource.endsWith(".cob") || !resource.startsWith(startWith)) {
					continue;
				}
				allFilenames.add(resource.substring(0, resource.length() - 4));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Cluster mit Dateigruppen erstellen
		Iterator<String> iter = allFilenames.iterator();
		while (iter.hasNext()) {
			List<String> group = new ArrayList<>();
			String firstItem = iter.next();
			group.add(firstItem);
			while (iter.hasNext()) {
				String nextItem = iter.next();
				if (nextItem.startsWith(firstItem + "-")) {
					// Folgeeintrag ist vorne identisch;
					group.add(nextItem);
				} else {
					// nächste Gruppe
					result.add(group);
					firstItem = nextItem;
					group = new ArrayList<>();
					group.add(firstItem);
				}
			}
			result.add(group);
		}
		return result;
	}

	private InputStream getResourceAsStream(String resource) {
		final InputStream in = getContextClassLoader().getResourceAsStream(resource);

		return in == null ? getClass().getResourceAsStream(resource) : in;
	}

	private ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
