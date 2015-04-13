import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

//@author A0111867A
public class HistoryTest {

	private History history;
	private UndoCmd undoCmd;
	private ExitCmd exitCmd;
	
	@Before
	public void setUp() throws Exception {
		history = History.getInstance();
		
		undoCmd = new UndoCmd();
		exitCmd = new ExitCmd();
	}
	
	@Test
	public void testAdd() {
		history.add(undoCmd);
		assertEquals(undoCmd.getClass(), history.peep().getClass());
	}

	@Test
	public void testRemove() {
		history.add(exitCmd);
		history.remove();
		assertEquals(undoCmd.getClass(), history.peep().getClass());
	}
		
}
