import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TodayViewTest {

	private String addTodayTask1 = "add Essay by 10am";
	private String addTodayTask2 = "add Maths Lecture from 11am to 1pm";
	private String addTodayTask3 = "add Read The Great Gatsby from page 30 to page 40 from 4pm to 5pm";
	private String addTodayTask4 = "add Lunch with Friends by 2pm";

	private String deleteTask1 = "delete 1";
	private String deleteTask3 = "delete 3";
	
	private String editDesTask2 = "edit 2 Software Engineering Lecture";

	private String markDoneTask3 = "done 3";
	
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
	public void testAddingTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addTodayTask1);

		control.executeCommand(addTodayTask2);

		control.executeCommand(addTodayTask3);

		control.executeCommand(addTodayTask4);

		TodayView today = new TodayView();
		String actualResult = today.getTodayList().toString();
		String expectedResult = "[[10:00] Essay , [11:00 - 13:00] Maths Lecture , [14:00] Lunch with Friends , [16:00 - 17:00] Read The Great Gatsby from page 30 to page 40 ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 6; i++) {
			control.executeCommand(deleteTask1);
		}
	}

	@Test
	public void testDeletingTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addTodayTask1);

		control.executeCommand(addTodayTask2);

		control.executeCommand(addTodayTask3);

		control.executeCommand(addTodayTask4);

		control.executeCommand(deleteTask3);

		TodayView today = new TodayView();
		String actualResult = today.getTodayList().toString();
		String expectedResult = "[[10:00] Essay , [11:00 - 13:00] Maths Lecture , [16:00 - 17:00] Read The Great Gatsby from page 30 to page 40 ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 6; i++) {
			control.executeCommand(deleteTask1);
		}
	}
	
	@Test
	public void testEditingTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addTodayTask1);

		control.executeCommand(addTodayTask2);

		control.executeCommand(addTodayTask3);

		control.executeCommand(addTodayTask4);

		control.executeCommand(editDesTask2);

		TodayView today = new TodayView();
		String actualResult = today.getTodayList().toString();
		String expectedResult = "[[10:00] Essay , [11:00 - 13:00] Software Engineering Lecture, [14:00] Lunch with Friends , [16:00 - 17:00] Read The Great Gatsby from page 30 to page 40 ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 6; i++) {
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

		control.executeCommand(markDoneTask3);

		TodayView today = new TodayView();
		String actualResult = today.getTodayList().toString();
		String expectedResult = "[[10:00] Essay , [11:00 - 13:00] Maths Lecture , [16:00 - 17:00] Read The Great Gatsby from page 30 to page 40 ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 6; i++) {
			control.executeCommand(deleteTask1);
		}
	}

}
