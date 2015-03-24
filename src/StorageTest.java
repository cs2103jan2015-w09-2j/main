//@author A0111217
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class StorageTest {
	
	private static final String FILE_NAME_NEW = "threeTag.json";
	private static final String MESSAGE_FILE_NAME_SET = "File name has been set to " + FILE_NAME_NEW;
	private static final String TEST_SET_PATH = "test setPath";
	private static final String MESSAGE_DIRECTORY_SET = "Directory has been set to D:\\School\\JavaProg\\CS2103\\Storage\\twoTag.json";
	private static final String MESSAGE_TEST_WRITING_DATA = "test writing data";
	private static final String MESSAGE_TEST_GETTING_DATA = "test getting data";
	private static final String TASK_BUY_ORANGE = "Buy orange";
	private static final String TASK_GO_RUNNING = "Go running";
	private static final String TASK_GO_HOME = "Go Home";
	private static final String TASK_GO_RT = "Go RT";
	private static final String TASK_MOP_FLOOR = "Mop floor";
	private static final String CHARACTER_BACKSLASH = "\\";
	
	private String currentRelativePath = System.getProperty(USER_DIRECTORY);
	private String fileName = "twoTag.json";
	private String filePath = currentRelativePath + CHARACTER_BACKSLASH + fileName;
	private static final String USER_DIRECTORY = "user.dir";
	
	
	private Task floatingTask;
	private Task earlyTimedTask;
	private Task lateTimedTask;
	private Task earlyDeadlineTask;
	private Task lateDeadlineTask;
	private ArrayList<Task> toDo = new ArrayList<Task>();
	private Storage storage;

	@Before
	public void setUp() throws Exception {
		floatingTask = new Task(TASK_BUY_ORANGE);
		earlyTimedTask = new Task(2015, 3, 10, 19, 0, 2015, 3, 10, 19, 30, TASK_GO_RUNNING);
		lateTimedTask = new Task(2015, 3, 10, 20, 0, 2015, 3, 10, 20, 30, TASK_GO_HOME);
		earlyDeadlineTask = new Task(2015, 3, 10, 21, 30, TASK_GO_RT);
		lateDeadlineTask = new Task(2015, 3, 10, 22, 30, TASK_MOP_FLOOR);
		
		toDo.add(floatingTask);
		toDo.add(earlyTimedTask);
		toDo.add(lateTimedTask);
		toDo.add(earlyDeadlineTask);
		toDo.add(lateDeadlineTask);
		
		storage = new Storage(toDo);
	}

	@Test
	public void testSetPath() {
		assertEquals(TEST_SET_PATH, storage.setPath(filePath), MESSAGE_DIRECTORY_SET);
	}

	@Test
	public void testSetFileName() {
		assertEquals("test setFileName", storage.setFileName(FILE_NAME_NEW), MESSAGE_FILE_NAME_SET);
	}

	@Test
	public void testWriteToFile() {
		storage.writeToFile(toDo);
		assertTrue(MESSAGE_TEST_WRITING_DATA, storage.getData().equals(toDo));
	}

	@Test
	public void testGetData() {
		assertTrue(MESSAGE_TEST_GETTING_DATA, storage.getData().equals(toDo));
	}

}
