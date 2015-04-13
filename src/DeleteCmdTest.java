import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

//@author A0111867A
public class DeleteCmdTest {
	
	private static final String TASK_GO_HIKING = "Go Hiking";
	
	protected static final String DELETE_TASK_MESSAGE = "Deleted <font color=\"#CC3300\"><i>%1$s</i></font>";
	protected static final String UNDO_DELETE_MESSAGE = "Undo action: <font color=\"#CC3300\"><i>\"Delete %1$s\"</i></font>";

	Data data;
	Storage storage;
	Display display;
	
	DeleteCmd deleteCmd, sameDeleteCmd, anotherDeleteCmd;
	Task task;
	ArrayList<Task> beforeList, afterList; 
	String deleteMessage, undoMessage;
	
	@Before
	public void setUp() throws Exception {
		data = Data.getInstance();
		storage = new Storage();
		display = Display.getInstance();
		
		task = new Task(TASK_GO_HIKING);
		
		deleteCmd = new DeleteCmd(1);
		sameDeleteCmd = new DeleteCmd(1);
		anotherDeleteCmd = new DeleteCmd(2);
		
		beforeList = new ArrayList<Task>();
		afterList = new ArrayList<Task>();
		
		beforeList.add(task);
		
		deleteMessage = String.format(DELETE_TASK_MESSAGE, task.getDescription(), Cmd.getTaskType(task));
		undoMessage = String.format(UNDO_DELETE_MESSAGE, task.getDescription());
		
		data.set(beforeList);
		display.getView().update();
	}
	
	@Test
	public void testExecute() {
		deleteCmd.execute();
		assertEquals("data", data.getData(), afterList);
		try {
			assertEquals("file", afterList, storage.getData());
		} catch (IOException e) {
			assert false;
		}
		assertEquals("message", deleteMessage, display.getMessage());
		System.out.println(data.getData());
	}

	@Test
	public void testUndo() {
		deleteCmd.execute();
		deleteCmd.undo();
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
		assertTrue("same cmd", deleteCmd.equals(sameDeleteCmd));
		assertFalse("different cmd", deleteCmd.equals(anotherDeleteCmd));
	}
	
}
