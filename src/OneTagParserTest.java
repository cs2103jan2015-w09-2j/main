import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;


public class OneTagParserTest {
	private static final String ADD_1 = "add pick up boiboi";
	private AddCmd addCmd;
	private Task task;
	private OneTagParser testParser;

	
	@Before
	public void initialize() {
		task = new Task(ADD_1);
		addCmd = new AddCmd(task);
		testParser = new OneTagParser(ADD_1);
	}
	
	@Test
	public void test() {
		assertTrue(addCmd.equals(testParser.toCmd()));
		fail("Not yet implemented");
	}
}
