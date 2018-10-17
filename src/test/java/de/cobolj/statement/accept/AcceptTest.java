package de.cobolj.statement.accept;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import de.cobolj.CobolBaseTest;
import de.cobolj.CobolExec;
import mockit.Mock;
import mockit.MockUp;

/**
 * Testfälle für das ACCEPT Statement
 * 
 * @author flaechsig
 *
 */
public class AcceptTest extends CobolBaseTest {
	private static int ANZAHL_TESTFAELLE = 1;

	static {
		new MockUp<LocalDateTime>() {
			@Mock
			public LocalDateTime now() {
				return LocalDateTime.of(2018, Month.APRIL, 9, 14, 44, 33, 555555555);
			}
		};
	}

	/**
	 * @return Liste aller Test-Objekte
	 */
	@DataProvider(name = "cobolTests")
	public Object[] acceptTest() {
		ArrayList<String> result = new ArrayList<>();
		for (int i = 1; i <= ANZAHL_TESTFAELLE; i++) {
			result.add("accept-" + i);
		}
		return result.toArray();
	}

	@Test(dataProvider = "dateTests")
	public void dateTests(String name, String param, String expected) throws IOException {
		InputStream fileStream = this.getClass().getResourceAsStream("accept-date-tmpl.cob");
		String fileString = IOUtils.toString(fileStream, Charset.defaultCharset());
		fileString = fileString.replace("$$PARAM$$", param);
		ByteArrayInputStream bis = new ByteArrayInputStream(fileString.getBytes());
		InputStreamReader fileReader = new InputStreamReader(bis);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		CobolExec.execute(fileReader, System.in, out);
		Assert.assertEquals(StringUtils.trim(out.toString()), expected, name);
	}

	@DataProvider(name = "dateTests")
	public Object[][] acceptDateTests() {
		return new Object[][] { { "DATE", "DATE", "180409" }, { "DATE YYYYMMDD", "DATE YYYYMMDD", "20180409" },
				{ "YYYYMMDD", "YYYYMMDD", "20180409" }, { "DAY", "DAY", "18099" },
				{ "DAY YYYYDDD", "DAY YYYYDDD", "2018099" }, { "YYYYDDD", "YYYYDDD", "2018099" },
				{ "DAY_OF_WEEK", "DAY-OF-WEEK", "1" }, { "TODAYS-DATE", "TODAYS-DATE", "040918" },
				{ "TODAYS-DATE MMDDYYYY", "TODAYS-DATE MMDDYYYY", "04092018" }, { "YEAR", "YEAR", "2018" },
				{ "TIME", "TIME", "14443355" }, { "TIMER", "TIMER", "2211398148" },
				{ "TODAYS-NAME", "TODAYS-NAME", "Monday" },

		};
	}
}
