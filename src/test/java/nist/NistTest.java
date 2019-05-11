package nist;

import org.testng.annotations.Test;

import de.cobolj.CobolExec;

public class NistTest {

	@Test
	public void testExec85()  {
		CobolExec.register("/nist/EXEC85.cob");
		CobolExec.execute(System.in, System.out, "EXEC85");
	}
}
