import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

//@author A0111867A
public class EditCmdTest {
	
	private static final String TASK_GO_HIKING = "Go Hiking";
	private static final String TASK_BUY_ORANGE = "Buy orange";
	
	protected static final String EDIT_DES_MESSAGE = "Task description is changed to <font color=\"#CC3300\"><i>%1$s</i></font>";
	protected static final String UNDO_EDIT_DES_MESSAGE = "Undo action: Task description is changed to <font color=\"#CC3300\"><i>%1$s</i></font>";
	
	Data data;
	Storage storage;
	Display display;
	
	EditCmd editCmd, sameEditCmd, anotherEditCmd;
	Task task, editedTask;
	ArrayList<Task> beforeList, afterList; 
	String editedMessage, undoMessage;
	
	@Before
	public void setUp() throws Exception {
		data = Data.getInstance();
		storage = new Storage();
		display = Display.getInstance();
		
		task = new Task(TASK_GO_HIKING);
		editedTask = new Task(TASK_BUY_ORANGE);
		
		editCmd = new EditCmd(1, TASK_BUY_ORANGE);
		sameEditCmd = new EditCmd(1, TASK_BUY_ORANGE);
		anotherEditCmd = new EditCmd(2, TASK_BUY_ORANGE);
		
		beforeList = new ArrayList<Task>();
		afterList = new ArrayList<Task>();
		
		beforeList.add(task);
		afterList.add(editedTask);
		
		editedMessage = String.format(EDIT_DES_MESSAGE, editedTask.getDescription(), Cmd.getTaskType(editedTask));
		undoMessage = String.format(UNDO_EDIT_DES_MESSAGE, task.getDescription());
		
		data.set(beforeList);
		display.set("");
	}
	
	@Test
	public void testExecute() {
		editCmd.execute();
		assertEquals("data", data.getData(), afterList);
		try {
			assertEquals("file", storage.getData(), afterList);
		} catch (IOException e) {
			assert false;
		}
		assertEquals("message", editedMessage, display.getMessage());
		System.out.println(data.getData());
	}

	@Test
	public void testUndo() {
		editCmd.execute();
		editCmd.undo();
		assertEquals("data", beforeList, data.getData());
		try {
			assertEquals("file", storage.getData(), beforeList);
		} catch (IOException e) {
			assert false;
		}
		assertEquals("message", undoMessage, display.getMessage());
	}
	
	
	@Test
	public void testEquals() {
		assertTrue("same cmd", editCmd.equals(sameEditCmd));
		assertFalse("different cmd", editCmd.equals(anotherEditCmd));
	}
	
}
