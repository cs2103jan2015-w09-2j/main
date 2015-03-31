//@author A0112715
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;


public class OneTagTest {
	
	private Controller control = Controller.getInstance();
	private LocalDateTime today = LocalDateTime.now();
	private String todayhr  = "0"+String.valueOf(today.getHour())+":";
	private String todayMin = String.valueOf(today.getMinute());
	
	@Test
	public void addTasks(){
		UserInterface UI = UserInterface.getInstance();
		UI.run();
		//tasks for today
		control.executeCommand("add complete integration testing by 2am");
		control.executeCommand("add Software engineering tutorial from 9am to 10am");
		control.executeCommand("add Stats Tutorial from 11am to 12pm");
		control.executeCommand("add Lunch with Wan Tian from 1pm to 2pm");
		
		//tasks for upcoming
		control.executeCommand("add Resize buttons by 02/04/2015");
		control.executeCommand("add AI Assignment by 13 April 2015");
		control.executeCommand("add Complete Essay by 15 April 2015");
		control.executeCommand("add Complete Revision by 20/05/2015");
		
		//tasks for Someday
		control.executeCommand("add Learn swimming");
		control.executeCommand("add Go skydiving");
		control.executeCommand("add Read Great Gatsby");
	}
	
	
	@Test
	public void doneTasks(){
		control.executeCommand("done 1");
		control.executeCommand("done 4");
	}

	@Test
	public void deleteTasks(){
		//delete for today
		control.executeCommand("delete 1");
		
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
