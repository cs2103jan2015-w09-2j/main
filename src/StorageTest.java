
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class StorageTest {
	
	private static final String MESSAGE_TEST_SET_PATH = "test setPath";
	private static final String MESSAGE_DIRECTORY_SET = "Directory has been set to D:\\";
	private static final String DIRECTORY_NEW = "D:\\";
	private static final String MESSAGE_TEST_WRITING_AND_GETTING_DATA = "test writing and getting data";
	private static final String MESSAGE_TEST_FILE_EXIST = "check if file is at new location";
	private static final String TASK_BUY_ORANGE = "Buy orange";
	private static final String TASK_GO_RUNNING = "Go running";
	private static final String TASK_GO_HOME = "Go Home";
	private static final String TASK_GO_RT = "Go RT";
	private static final String TASK_MOP_FLOOR = "Mop floor";
	private static final String CHARACTER_BACKSLASH = "\\";
	
	private String currentRelativePath = System.getProperty(USER_DIRECTORY);
	private String fileName = "oneTag.json";
	private String filePath = currentRelativePath + CHARACTER_BACKSLASH + fileName;
	private String configFileName = "config.json";
	private static final String USER_DIRECTORY = "user.dir";
	private File config = new File(configFileName);
	
	private Task floatingTask;
	private Task earlyTimedTask;
	private Task lateTimedTask;
	private Task earlyDeadlineTask;
	private Task lateDeadlineTask;
	private ArrayList<Task> toDo = new ArrayList<Task>();
	private ArrayList<Task> emptyList = new ArrayList<Task>();
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
		
		if (config.exists()){
			config.delete();
		}
		storage = new Storage(filePath, toDo);
		
	}

	@Test
	public void testWriteToFileAndGetFromFile() {
		
		//Writes file with empty arraylist
		storage.writeToFile(emptyList);
		assertEquals(MESSAGE_TEST_WRITING_AND_GETTING_DATA, storage.getData(), emptyList);
		// Writes file with ArrayList<Task>
		storage.writeToFile(toDo);
		assertTrue(MESSAGE_TEST_WRITING_AND_GETTING_DATA, storage.getData().equals(toDo));
		// Writes file with null arraylist
		storage.writeToFile(null);
		assertEquals(MESSAGE_TEST_WRITING_AND_GETTING_DATA, storage.getData(), emptyList);
	}
	
	@Test
	public void testSetPath() {
		//Partition for location changed successfully
		assertEquals(MESSAGE_TEST_SET_PATH, storage.setPath(DIRECTORY_NEW), MESSAGE_DIRECTORY_SET);
		filePath = DIRECTORY_NEW + "oneTag.json";
		File changedLocation = new File(filePath);
		assertTrue(MESSAGE_TEST_FILE_EXIST, changedLocation.exists());
		
		
	}



}
