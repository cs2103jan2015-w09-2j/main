import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

//@author A0111867A
public class AddCmdTest {
	
	private static final String TASK_GO_HIKING = "Go Hiking";
	private static final String TASK_BUY_ORANGE = "Buy orange";
	
	protected static final String ADD_TASK_MESSAGE = "Added <font color=\"#CC3300\"><i>%1$s</i></font> to <font color=\"#CC3300\"><i>%2$s</i></font>";
	protected static final String UNDO_ADD_MESSAGE = "Undo action: <font color=\"#CC3300\"><i>\"Add %1$s\"</i></font>";
	
	Data data;
	Storage storage;
	Display display;
	
	AddCmd addCmd, sameAddCmd, anotherAddCmd;
	Task task, anotherTask;
	ArrayList<Task> emptyList, list;
	String addMessage, undoMessage;
	
	@Before
	public void setUp() throws Exception {
		data = Data.getInstance();
		storage = new Storage();
		display = Display.getInstance();
		
		task = new Task(TASK_GO_HIKING);
		anotherTask = new Task(TASK_BUY_ORANGE);
		addCmd = new AddCmd(task);
		sameAddCmd = new AddCmd(task);
		anotherAddCmd = new AddCmd(anotherTask);
		
		list = new ArrayList<Task>();
		list.add(task);
		
		emptyList = new ArrayList<Task>();
		
		addMessage = String.format(ADD_TASK_MESSAGE, task.getDescription(), Cmd.getTaskType(task));
		undoMessage = String.format(UNDO_ADD_MESSAGE, task.getDescription());
	}
	
	@Test
	public void testExecute() {
		addCmd.execute();
		assertEquals("data", data.getData(), list);
		try {
			assertEquals("file", storage.getData(), list);
		} catch (IOException e) {
			assert false;
		}
		assertEquals("message", addMessage, display.getMessage());
	}

	@Test
	public void testUndo() {
		addCmd.undo();
		assertEquals("data", data.getData(), emptyList);
		try {
			assertEquals("file", storage.getData(), emptyList);
		} catch (IOException e) {
			assert false;
		}
		assertEquals("message", undoMessage, display.getMessage());
	}
	
	
	@Test
	public void testEquals() {
		assertTrue("same cmd", addCmd.equals(sameAddCmd));
		assertFalse("different cmd", addCmd.equals(anotherAddCmd));
	}
	
}
