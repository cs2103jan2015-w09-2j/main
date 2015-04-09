//@author A0111217Y
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class StorageTest {
	
	private static final String DIRECTORY_NEW = "D:\\";
	private static final String MESSAGE_TEST_WRITING_AND_GETTING_DATA = "test writing and getting data";
	private static final String MESSAGE_TEST_FILE_EXIST = "check if file is at new location";
	private static final String MESSAGE_TEST_GET_PATH = "test getting the file path";
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
	public void oneTimeSetUp() throws Exception {
		floatingTask = new Task(TASK_BUY_ORANGE);
		earlyTimedTask = new Task(LocalDateTime.of(2015, 3, 10, 19, 0), LocalDateTime.of(2015, 3, 10, 19, 30), TASK_GO_RUNNING);
		lateTimedTask = new Task(LocalDateTime.of(2015, 3, 10, 20, 0), LocalDateTime.of(2015, 3, 10, 20, 30), TASK_GO_HOME);
		earlyDeadlineTask = new Task(LocalDateTime.of(2015, 3, 10, 21, 30), TASK_GO_RT);
		lateDeadlineTask = new Task(LocalDateTime.of(2015, 3, 10, 22, 30), TASK_MOP_FLOOR);
		
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
	
	@After
	public void tearDown() throws Exception{
		File storageFile = new File(filePath);
		if (storageFile.exists()){
			storageFile.delete();
		}
		if (config.exists()){
			config.delete();
		}
	}
	
	@Test
	public void testGetPath(){
		//Only one partition because file path can never be set to null assertions in code
		assertEquals(MESSAGE_TEST_GET_PATH, storage.getPath(), filePath);

	}
	
	@Test
	public void testGetFilePath(){
		//Path can never be null or empty
		assertEquals(MESSAGE_TEST_GET_PATH, storage.getFilePath(), currentRelativePath + CHARACTER_BACKSLASH);
	}
	
	@Test
	public void testWriteToFileAndGetFromFile() throws IOException {
		ArrayList<Task> list = null;
		
		// Writes file with null arraylist
		try{
		storage.writeToFile(null);
		}catch (IOException ex){
			assert false;
		}
		try{
			list = storage.getData();
		}catch(IOException ex){
			assert false;
		}
		assertEquals(MESSAGE_TEST_WRITING_AND_GETTING_DATA, list, emptyList);
		
		//Writes file with empty arraylist
		try{
			storage.writeToFile(emptyList);
		}catch(IOException ex){
			assert false;
		}
		try{
			list = storage.getData();
		}catch(IOException ex){
			assert false;
		}
		assertEquals(MESSAGE_TEST_WRITING_AND_GETTING_DATA, list, emptyList);
		
		// Writes file with ArrayList<Task>
		try{
			storage.writeToFile(toDo);
		}catch(IOException ex){
			assert false;
		}
		try{
			list = storage.getData();
		}catch(IOException ex){
			assert false;
		}
		assertEquals(MESSAGE_TEST_WRITING_AND_GETTING_DATA, list, toDo);
		
	}
	
	@Test
	public void testSetPath() throws IOException {
		//Partition for location ending with "/" changed successfully
		storage.setPath(DIRECTORY_NEW);
		filePath = DIRECTORY_NEW + "oneTag.json";
		File changedLocation = new File(filePath);
		assertTrue(MESSAGE_TEST_FILE_EXIST, changedLocation.exists());
		
		//Partition for location not ending with "/" changed successfully
		filePath = currentRelativePath;
		storage.setPath(filePath);
		changedLocation = new File(filePath);
		assertTrue(MESSAGE_TEST_FILE_EXIST, changedLocation.exists());

		
	}



}
