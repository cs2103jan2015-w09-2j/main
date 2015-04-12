import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class HomeViewTest {
	private String addTodayTask1 = "add Essay by 10am";
	private String addTodayTask2 = "add Maths Lecture from 11am to 1pm";
	private String addTodayTask3 = "add Read The Great Gatsby from page 30 to page 40 from 4pm to 5pm";
	private String addTodayTask4 = "add Lunch with Friends by 2pm";
	
	private String addUpcomingTask1 = "add Submit assignment by June 15 6pm";
	private String addUpcomingTask2 = "add Holiday with family in Rome from 11am 17 June to 1pm 27 June";
	private String addUpcomingTask3 = "add Dental appointment by 3pm 23 July";
	private String addUpcomingTask4 = "add Birthday Celebration from 06 july 2pm to 06 july 7pm";

	private String addSomedayTask1 = "add Buy belated birthday gifts";
	private String addSomedayTask2 = "add Go Skydiving";
	
	private String deleteTask3 = "delete 3";
	private String deleteTask5 = "delete 5";
	private String deleteTask7 = "delete 7";
	
	private String editDesTask2 = "edit 2 Software Engineering Lecture";

	private String markDoneTask1 = "done 1";
	private String markDoneTask5 = "done 5";
	private String markDoneTask6 = "done 6";
	
	private String deleteTask1="delete 1";

	@Before
	public void beforeTesting() {
		File file = new File("oneTag.json");
		File file2 = new File("config.json");
		file.delete();
		file2.delete();
	}

	@After
	public void clearAfterTesting() {
		File file = new File("oneTag.json");
		File file2 = new File("config.json");
		file.delete();
		file2.delete();

	}

	@Test
	public void testAddTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addTodayTask1);
		control.executeCommand(addTodayTask2);
		control.executeCommand(addTodayTask3);
		control.executeCommand(addTodayTask4);

		control.executeCommand(addSomedayTask1);
		control.executeCommand(addSomedayTask2);

		control.executeCommand(addUpcomingTask1);
		control.executeCommand(addUpcomingTask2);
		control.executeCommand(addUpcomingTask3);
		control.executeCommand(addUpcomingTask4);
		
		HomeView home = new HomeView();
		String actualResult = home.getList().toString();
		String expectedResult = "[[10:00] Essay , [11:00 - 13:00] Maths Lecture , [14:00] Lunch with Friends , [16:00 - 17:00] Read The Great Gatsby from page 30 to page 40 , [18:00] Submit assignment , [11:00 - 13:00] Holiday with family in Rome , [14:00 - 19:00] Birthday Celebration , [15:00] Dental appointment , Buy belated birthday gifts , Go Skydiving ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 10; i++) {
			control.executeCommand(deleteTask1);
		}
	}
	
	@Test
	public void testDeleteTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addTodayTask1);
		control.executeCommand(addTodayTask2);
		control.executeCommand(addTodayTask3);
		control.executeCommand(addTodayTask4);

		control.executeCommand(addSomedayTask1);
		control.executeCommand(addSomedayTask2);

		control.executeCommand(addUpcomingTask1);
		control.executeCommand(addUpcomingTask2);
		control.executeCommand(addUpcomingTask3);
		control.executeCommand(addUpcomingTask4);
		
		control.executeCommand(deleteTask3);
		control.executeCommand(deleteTask5);
		control.executeCommand(deleteTask7);
		
		HomeView home = new HomeView();
		String actualResult = home.getList().toString();
		String expectedResult = "[[10:00] Essay , [11:00 - 13:00] Maths Lecture , [16:00 - 17:00] Read The Great Gatsby from page 30 to page 40 , [18:00] Submit assignment , [14:00 - 19:00] Birthday Celebration , [15:00] Dental appointment , Go Skydiving ]";	
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 10; i++) {
			control.executeCommand(deleteTask1);
		}
	}

	@Test
	public void testEditTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addTodayTask1);
		control.executeCommand(addTodayTask2);
		control.executeCommand(addTodayTask3);
		control.executeCommand(addTodayTask4);

		control.executeCommand(addSomedayTask1);
		control.executeCommand(addSomedayTask2);

		control.executeCommand(addUpcomingTask1);
		control.executeCommand(addUpcomingTask2);
		control.executeCommand(addUpcomingTask3);
		control.executeCommand(addUpcomingTask4);
		
		control.executeCommand(editDesTask2);

		
		HomeView home = new HomeView();
		String actualResult = home.getList().toString();
		String expectedResult = "[[10:00] Essay , [11:00 - 13:00] Software Engineering Lecture, [14:00] Lunch with Friends , [16:00 - 17:00] Read The Great Gatsby from page 30 to page 40 , [18:00] Submit assignment , [11:00 - 13:00] Holiday with family in Rome , [14:00 - 19:00] Birthday Celebration , [15:00] Dental appointment , Buy belated birthday gifts , Go Skydiving ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 10; i++) {
			control.executeCommand(deleteTask1);
		}
	}
	
	@Test
	public void testMarkingDoneTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addTodayTask1);
		control.executeCommand(addTodayTask2);
		control.executeCommand(addTodayTask3);
		control.executeCommand(addTodayTask4);

		control.executeCommand(addSomedayTask1);
		control.executeCommand(addSomedayTask2);

		control.executeCommand(addUpcomingTask1);
		control.executeCommand(addUpcomingTask2);
		control.executeCommand(addUpcomingTask3);
		control.executeCommand(addUpcomingTask4);
		
		control.executeCommand(markDoneTask1);
		control.executeCommand(markDoneTask5);
		control.executeCommand(markDoneTask6);
		
		HomeView home = new HomeView();
		String actualResult = home.getList().toString();
		String expectedResult = "[[11:00 - 13:00] Maths Lecture , [14:00] Lunch with Friends , [16:00 - 17:00] Read The Great Gatsby from page 30 to page 40 , [18:00] Submit assignment , [14:00 - 19:00] Birthday Celebration , Buy belated birthday gifts , Go Skydiving ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 10; i++) {
			control.executeCommand(deleteTask1);
		}
	}
}
