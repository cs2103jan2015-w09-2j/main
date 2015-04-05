import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class AddCmdTest {
	
	private static final String TASK_GO_HIKING = "Go Hiking";
	protected static final String MESSAGE_ADD = "Added \"%1$s\" to %2$s”";
	protected static final String MESSAGE_UNDO_ADD = "Undo Add: \"%1$s\" is removed from task list ";
	
	Data data;
	Storage storage;
	Display display;
	
	AddCmd addCmd;
	Task task;
	ArrayList<Task> emptyList, list;
	String addMessage, undoMessage;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		data = Data.getInstance();
		storage = new Storage();
		display = Display.getInstance();
		
		task = new Task(TASK_GO_HIKING);
		addCmd = new AddCmd(task);
		
		list = new ArrayList<Task>();
		list.add(task);
		
		emptyList = new ArrayList<Task>();
		
		addMessage = String.format(MESSAGE_ADD, task.getDescription(), Cmd.getTaskType(task));
		undoMessage = String.format(MESSAGE_UNDO_ADD, task.getDescription());
	}
	
	@Test
	public void testExecute() {
		addCmd.execute();
		assertEquals("data", data.getData(), list);
		assertEquals("file", storage.getData(), list);
		assertEquals("message", display.getMessage(), addMessage);
	}

	@Test
	public void testUndo() {
		addCmd.undo();
		assertEquals("data", data.getData(), emptyList);
		assertEquals("file", storage.getData(), emptyList);
		assertEquals("message", display.getMessage(), undoMessage);
		
	}
	
}
