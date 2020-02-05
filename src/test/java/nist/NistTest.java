package nist;

import org.testng.annotations.Test;

import de.cobolj.CobolExec;

public class NistTest {

//	@Test
	public void testExec85()  {
		System.setProperty("XXXXX001", "target/cobTest/XXXXX001");
		System.setProperty("XXXXX002", "target/cobTest/XXXXX002");
		System.setProperty("XXXXX003", "target/cobTest/XXXXX003");
		System.setProperty("XXXXX055", "target/cobTest/XXXXX055");
		System.setProperty("XXXXX058", "target/cobTest/XXXXX058");
		CobolExec.register("/nist/EXEC85.cob");
		CobolExec.execute(System.in, System.out, "EXEC85");
	}
}
