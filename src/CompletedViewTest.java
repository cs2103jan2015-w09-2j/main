import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class CompletedViewTest {

	private String addTodayTask1 = "add Essay by 10am";
	private String addTodayTask2 = "add Maths Lecture from 11am to 1pm";
	
	private String addUpcomingTask1 = "add Submit assignment by June 15 6pm";
	private String addUpcomingTask2 = "add Holiday with family in Rome from 11am 17 June to 1pm 27 June";
	
	private String addSomedayTask1 = "add Buy belated birthday gifts";
	private String addSomedayTask2 = "add Go Skydiving";
	
	private String doneTask1 = "done 1";
	private String doneTask3 = "done 3";
	private String doneTask4 = "done 4";
	
	private String deleteTask1="delete 1";

	@BeforeClass
	public static void beforeTesting() {
		File file = new File("oneTag.json");
		File file2 = new File("config.json");
		file.delete();
		file2.delete();
		ArrayList<Task> arrayList = new ArrayList<Task>();
		Data data = Data.getInstance();
		data.set(arrayList);
	}

	@AfterClass
	public static void clearAfterTesting() {
		File file = new File("oneTag.json");
		File file2 = new File("config.json");
		file.delete();
		file2.delete();
		ArrayList<Task> arrayList = new ArrayList<Task>();
		Data data = Data.getInstance();
		data.set(arrayList);

	}

	@Test
	public void testCompletedTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addTodayTask1);
		control.executeCommand(addTodayTask2);

		control.executeCommand(addSomedayTask1);
		control.executeCommand(addSomedayTask2);

		control.executeCommand(addUpcomingTask1);
		control.executeCommand(addUpcomingTask2);
		
		control.executeCommand(doneTask1);

		control.executeCommand(doneTask3);
		control.executeCommand(doneTask4);
		
		CompletedView completed= new CompletedView();
		String actualResult = completed.getCompletedList().toString();
		String expectedResult = "[[10:00] Essay , Go Skydiving , [11:00 - 13:00] Holiday with family in Rome ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 6; i++) {
			control.executeCommand(deleteTask1);
		}
	}

}
