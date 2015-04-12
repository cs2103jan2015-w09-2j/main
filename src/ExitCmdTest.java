import static org.junit.Assert.*;

import org.junit.Test;

public class ExitCmdTest {
	
	private static final String FILE_DIRECTORY = "C:/CS2103";
	
	@Test
	public void testEqualsObject() {
		ExitCmd exitObject1 = new ExitCmd();
		ExitCmd exitObject2 = new ExitCmd();
		SaveCmd saveCmd = new SaveCmd(FILE_DIRECTORY);
		
		assertTrue(exitObject1.equals(exitObject2));
		assertFalse(saveCmd.equals(exitObject1));
	}
	
	@Test
	public void testExecute() {
		fail("Not Implemented");
		
	}

}
