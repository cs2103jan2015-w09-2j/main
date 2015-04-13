//@author A0112715R
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class UpcomingViewTest {

	private String addUpcomingTask1 = "add Submit assignment by June 15 6pm";
	private String addUpcomingTask2 = "add Holiday with family in Rome from 11am 17 June to 1pm 27 June";
	private String addUpcomingTask3 = "add Dental appointment by 3pm 23 July";
	private String addUpcomingTask4 = "add Birthday Celebration from 06 july 2pm to 06 july 7pm";

	private String deleteTask1 = "delete 1";
	private String deleteTask3 = "delete 3";
	
	private String editDesTask3 = "edit 3 Dental appointment at NDC";

	private String markDoneTask1 = "done 1";
	
	@Before
	public void beforeTesting() {
		File file = new File("oneTag.json");
		File file2 = new File("config.json");
		file.delete();
		file2.delete();
		ArrayList<Task> arrayList = new ArrayList<Task>();
		Data data = Data.getInstance();
		data.set(arrayList);
	}

	@After
	public void clearAfterTesting() {
		File file = new File("oneTag.json");
		File file2 = new File("config.json");
		file.delete();
		file2.delete();
		ArrayList<Task> arrayList = new ArrayList<Task>();
		Data data = Data.getInstance();
		data.set(arrayList);
	}

	@Test
	public void testAddingTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addUpcomingTask1);

		control.executeCommand(addUpcomingTask2);

		control.executeCommand(addUpcomingTask3);

		control.executeCommand(addUpcomingTask4);

		UpcomingView upcoming = new UpcomingView();
		String actualResult = upcoming.getUpcomingList().toString();
		String expectedResult = "[[18:00] Submit assignment , [11:00 - 13:00] Holiday with family in Rome , [14:00 - 19:00] Birthday Celebration , [15:00] Dental appointment ]";	
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 6; i++) {
			control.executeCommand(deleteTask1);
		}
	}

	@Test
	public void testDeletingTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addUpcomingTask1);

		control.executeCommand(addUpcomingTask2);

		control.executeCommand(addUpcomingTask3);

		control.executeCommand(addUpcomingTask4);

		control.executeCommand(deleteTask3);

		UpcomingView today = new UpcomingView();
		String actualResult = today.getUpcomingList().toString();
		String expectedResult = "[[18:00] Submit assignment , [11:00 - 13:00] Holiday with family in Rome , [15:00] Dental appointment ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 6; i++) {
			control.executeCommand(deleteTask1);
		}
	}
	
	@Test
	public void testEditingTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addUpcomingTask1);

		control.executeCommand(addUpcomingTask2);

		control.executeCommand(addUpcomingTask3);

		control.executeCommand(addUpcomingTask4);

		control.executeCommand(editDesTask3);

		UpcomingView today = new UpcomingView();
		String actualResult = today.getUpcomingList().toString();
		String expectedResult = "[[18:00] Submit assignment , [11:00 - 13:00] Holiday with family in Rome , [14:00 - 19:00] Dental appointment at NDC, [15:00] Dental appointment ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 6; i++) {
			control.executeCommand(deleteTask1);
		}
	}
	
	@Test
	public void testMarkingDoneTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addUpcomingTask1);

		control.executeCommand(addUpcomingTask2);

		control.executeCommand(addUpcomingTask3);

		control.executeCommand(addUpcomingTask4);

		control.executeCommand(markDoneTask1);

		UpcomingView today = new UpcomingView();
		String actualResult = today.getUpcomingList().toString();
		String expectedResult = "[[11:00 - 13:00] Holiday with family in Rome , [14:00 - 19:00] Birthday Celebration , [15:00] Dental appointment ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 6; i++) {
			control.executeCommand(deleteTask1);
		}
	}

}
