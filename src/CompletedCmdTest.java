import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

//@author A0111867A
public class CompletedCmdTest {
	
	private static final String TASK_GO_HIKING = "Go Hiking";
	
	protected static final String COMPLETE_TASK_MESSAGE = "<font color=\"#CC3300\"><i>%1$s</i> is marked as completed!";
	protected static final String UNDO_COMPLETED_MESSAGE = "Undo action: <font color=\"#CC3300\"><i>\"Mark %1$s as completed\"</i></font>";

	Data data;
	Storage storage;
	Display display;
	
	CompletedCmd completedCmd, sameCompletedCmd, anotherCompletedCmd;
	Task task, completedTask;
	ArrayList<Task> beforeList, afterList; 
	String completeMessage, undoMessage;
	
	@Before
	public void setUp() throws Exception {
		data = Data.getInstance();
		storage = new Storage();
		display = Display.getInstance();
		
		task = new Task(TASK_GO_HIKING);
		completedTask = new Task(TASK_GO_HIKING);
		completedTask.setIsCompleted(true);
		
		completedCmd = new CompletedCmd(1);
		sameCompletedCmd = new CompletedCmd(1);
		anotherCompletedCmd = new CompletedCmd(2);
		
		beforeList = new ArrayList<Task>();
		afterList = new ArrayList<Task>();
		
		beforeList.add(task);
		afterList.add(completedTask);
		
		completeMessage = String.format(COMPLETE_TASK_MESSAGE, task.getDescription(), Cmd.getTaskType(task));
		undoMessage = String.format(UNDO_COMPLETED_MESSAGE, task.getDescription());
		
		data.set(beforeList);
		display.set("");
	}
	
	@Test
	public void testExecute() {
		completedCmd.execute();
		assertEquals("data", data.getData(), afterList);
		try {
			assertEquals("file", storage.getData(), afterList);
		} catch (IOException e) {
			assert false;
		}
		assertEquals("message", completeMessage, display.getMessage());
		System.out.println(data.getData());
	}

	@Test
	public void testUndo() {
		completedCmd.execute();
		completedCmd.undo();
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
		assertTrue("same cmd", completedCmd.equals(sameCompletedCmd));
		assertFalse("different cmd", completedCmd.equals(anotherCompletedCmd));
	}
	
}
