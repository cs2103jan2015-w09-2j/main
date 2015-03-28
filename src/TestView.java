import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;


public class TestView {
	
	private Controller control = new Controller();
	
	@Test
	public void addTasksForToday(){
		//tasks for today
		control.executeCommand("add Software engineering tutorial from 9am to 10am");
		control.executeCommand("add Stats Tutorial from 11am to 12pm");
		control.executeCommand("add Lunch with Wan Tian from 1pm to 2pm");
		
		//tasks for upcoming
		control.executeCommand("add AI Assignment by 13 April 2015");
		control.executeCommand("add Complete Essay by 15 April 2015");
		control.executeCommand("add Complete Revision by 20/05/2015");
		
		//tasks for Someday
		control.executeCommand("add Read Last Phone Call From Heaven");
		control.executeCommand("add Go skydiving one day");
	}
	
	//Equivalence Partitioning 
	
	//Partition 1 : Checking the view for today
	@Test
	public void testTodayView() {
		TodayView today = new TodayView();
		String todayTasks  = today.getToday();
		ArrayList<String> testTodayTasks = new ArrayList<String>();
		testTodayTasks.add("[09:00 - 10:00] Software engineering tutorial ");
		testTodayTasks.add("[11:00 - 12:00] Stats Tutorial ");
		testTodayTasks.add("[13:00 - 14:00] Lunch with Wan Tian ");
		String testToday = getTask(testTodayTasks);
		assertEquals(testToday,todayTasks);
		String getTaskByNo = today.getTask(2).toString();
		assertEquals(getTaskByNo,"[11:00 - 12:00] Stats Tutorial ");
	}
	
	@Test
	public void testSomedayView() {
		SomedayView someday = new SomedayView();
		String somedayTasks  = someday.getSomeday();
		ArrayList<String> testSomedayTasks = new ArrayList<String>();
		testSomedayTasks.add("ReadLast Phone Call From Heaven ");
		testSomedayTasks.add("Goskydiving one day ");
		String testSomeday = getTask(testSomedayTasks);
		assertEquals(testSomeday,somedayTasks);
		String getTaskByNo = someday.getTask(2).toString();
		assertEquals(getTaskByNo,"Goskydiving one day ");
	}
	
	@Test
	public void testUpcomingView() {
		UpcomingView upcoming = new UpcomingView();
		String upcomingTasks  = upcoming.getUpcoming();
		LocalDateTime today = LocalDateTime.now();
		String todayhr  = "0"+String.valueOf(today.getHour())+":";
		String todayMin = String.valueOf(today.getMinute());
		ArrayList<String> testUpcomingTasks = new ArrayList<String>();
		testUpcomingTasks.add("["+todayhr+todayMin+"] AI Assignment ");
		testUpcomingTasks.add("["+todayhr+todayMin+"] Complete Essay ");
		testUpcomingTasks.add("["+todayhr+todayMin+"] Complete Revision ");
		String testUpcoming = getTask(testUpcomingTasks);
		System.out.println(testUpcomingTasks);
		assertEquals(testUpcoming,upcomingTasks);
		String getTaskByNo = upcoming.getTask(3).toString();
		assertEquals(getTaskByNo,"["+todayhr+todayMin+"] Complete Revision ");
		
	}
	
	
	
	protected String getTask(ArrayList<String> list) {
		String tasks = "";
		int i =0;
		for (String task : list) {
			i++;
			tasks += "  "+i + ". " + task.toString() + "\n";
		}
		return tasks;
	}

}
