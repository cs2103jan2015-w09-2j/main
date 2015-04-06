//@author A0112715
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;


public class OneTagTest {
	
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
	private LocalDateTime today = LocalDateTime.now();
	private String todayhr  = "0"+String.valueOf(today.getHour())+":";
	private String todayMin = String.valueOf(today.getMinute());
	
	@Test
	public void addTasks(){
		UserInterface UI = UserInterface.getInstance();
		UI.executeInterface();
		//tasks for today
		control.executeCommand(ADD_TASK_INTEGRATION);
		control.executeCommand(ADD_TASK_SOFTWARE_ENGINEERING);
		
		//tasks for upcoming
		control.executeCommand(ADD_TASK_COMPLETE_ESSAY);
		control.executeCommand(ADD_TASK_COMPLETE_REVISION);
		
		//tasks for Someday
		control.executeCommand(ADD_TASK_SWIMMING);
		control.executeCommand(ADD_TASK_SKYDIVING);
		control.executeCommand(ADD_TASK_READ_GATSBY);
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
		control.executeCommand(SEARCH_COMPLETE);
	
	}
	
	@Test
	public void save(){
		control.executeCommand(ADD_TASK_INTEGRATION);
		control.executeCommand(SAVE_D);
		File storageFile = new File(FILE_NAME_STORAGE);
		assertTrue(storageFile.exists());
	}
	
	@Test
	public void undo(){
		control.executeCommand(ADD_TASK_INTEGRATION);
		control.executeCommand(UNDO);
	}
	
	
	@Test
	public void doneTasks(){
		control.executeCommand("done 1");
		control.executeCommand("done 4");
	}

	@Test
	public void deleteTasks(){
		//delete for today
		control.executeCommand(DELETE_1);
		
		//delete for someday
		control.executeCommand("delete 5");
		
		//delete for upcoming
		control.executeCommand("delete 7");
	}

	@Test
	public void testTodayView() {
		TodayView today = new TodayView();
		String todayTasks  = today.getList().toString();
		String testToday = "[[02:00] complete integration testing , [09:00 - 10:00] Software engineering tutorial , [11:00 - 12:00] Stats Tutorial , [13:00 - 14:00] Lunch with Wan Tian ]";
		assertEquals(testToday,todayTasks);

	}
	
	@Test
	public void testUpcomingView() {
		UpcomingView upcoming = new UpcomingView();
		String upcomingTasks  = upcoming.getList().toString();
		String testUpcoming = "[["+todayhr+todayMin+"] Resize buttons , ["+todayhr+todayMin+"] Complete Essay , ["+todayhr+todayMin+"] Complete Revision ]";
		assertEquals(testUpcoming,upcomingTasks);
		}
	
	@Test
	public void testSomedayView() {
		SomedayView someday = new SomedayView();
		String somedayTasks  = someday.getList().toString();
		String testSomeday = "[Go skydiving , Read Great Gatsby ]";
		assertEquals(testSomeday,somedayTasks);
	
	}
	
	@Test
	public void testCompletedView() {
		CompletedView completed = new CompletedView();
		String completedTasks  = completed.getList().toString();
		String testCompleted = "[[09:00 - 10:00] Software engineering tutorial , ["+todayhr+todayMin+"] Complete Essay ]";
		assertEquals(testCompleted,completedTasks);
	
	}
	

}
