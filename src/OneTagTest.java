//@author A0112715
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;


public class OneTagTest {
	
	private static final String ADD_DATELINE = "add market proposal for boss by 6pm";
	private static final String ADD_TIMED = "add meeting with Mr Lee from 8 april 11am to 8 april 1pm";
	private static final String ADD_FLOATING = "add bring daughter for skating";
	private static final String EDIT_SIMPLE = "edit 1 by 5pm";
	private static final String EDIT_COMPLEX = "edit 2 meeting with Mrs Lee to 2pm";
	private static final String COMPLETE = "done 1";

	private static final String SEARCH_COMPLETE = "search complete";
	private static final String ADD_TASK_COMPLETE_REVISION = "add Complete Revision by 20/05/2015";
	private static final String ADD_TASK_COMPLETE_ESSAY = "add Complete Essay by 15 April 2015";
	private static final String FILE_NAME_STORAGE = "oneTag.json";
	private static final String SAVE_D = "save D:/";
	private static final String DELETE_1 = "delete 1";
	private static final String ADD_TASK_SOFTWARE_ENGINEERING = "add Software engineering tutorial from 9am to 10am";
	private static final String ADD_TASK_INTEGRATION = "add complete integration testing by 2am";
	private static final String ADD_TASK_SWIMMING = "add Learn swimming";
	private static final String ADD_TASK_SKYDIVING = "add Go skydiving";
	private static final String ADD_TASK_READ_GATSBY = "add Read The Great Gatsby";
	private static final String UNDO = "undo";
	
	private Controller control = Controller.getInstance();
	private Display display = Display.getInstance();
	
	@Test
	public void addDateline(){
		control.executeCommand(ADD_DATELINE);
		assertEquals("add dateline task", display.getView().getList(), "");
	}
	
	@Test
	public void deleteTask(){
		
		control.executeCommand(ADD_TASK_INTEGRATION);
		control.executeCommand(ADD_TASK_SOFTWARE_ENGINEERING);
		control.executeCommand(DELETE_1);
		
	}
	
	@Test
	public void search(){
		control.executeCommand(ADD_TASK_COMPLETE_ESSAY);
		control.executeCommand(ADD_TASK_COMPLETE_REVISION);
		//control.executeCommand(SEARCH_COMPLETE);
	
	}
	
	@Test
	public void save(){
		control.executeCommand(ADD_TASK_INTEGRATION);
		control.executeCommand(SAVE_D);
		File storageFile = new File(FILE_NAME_STORAGE);
		//assertTrue(storageFile.exists());
	}
	
	@Test
	public void undo(){
		control.executeCommand(ADD_TASK_INTEGRATION);
		control.executeCommand(UNDO);
	}
	
}
