//@author A0112715R

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SomedayViewTest {

	private String addSomedayTask1 = "add Learn French";
	private String addSomedayTask2 = "add Buy belated birthday gifts";
	private String addSomedayTask3 = "add Go Skydiving";
	private String addSomedayTask4 = "add Lunch with Friends by 2pm";

	private String deleteTask1 = "delete 1";
	private String deleteTask3 = "delete 3";
	
	private String editDesTask3 = "edit 3 Try to go skydiving at least once";

	private String markDoneTask2 = "done 2";
	
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
		control.executeCommand(addSomedayTask1);

		control.executeCommand(addSomedayTask2);

		control.executeCommand(addSomedayTask3);

		control.executeCommand(addSomedayTask4);

		SomedayView today = new SomedayView();
		String actualResult = today.getSomedayList().toString();
		String expectedResult = "[Learn French , Buy belated birthday gifts , Go Skydiving ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 6; i++) {
			control.executeCommand(deleteTask1);
		}
	}

	@Test
	public void testDeletingTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addSomedayTask1);

		control.executeCommand(addSomedayTask2);

		control.executeCommand(addSomedayTask3);

		control.executeCommand(addSomedayTask4);

		control.executeCommand(deleteTask3);

		SomedayView today = new SomedayView();
		String actualResult = today.getSomedayList().toString();
		String expectedResult = "[Learn French , Go Skydiving ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 6; i++) {
			control.executeCommand(deleteTask1);
		}
	}
	
	@Test
	public void testEditingTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addSomedayTask1);

		control.executeCommand(addSomedayTask2);

		control.executeCommand(addSomedayTask3);

		control.executeCommand(addSomedayTask4);

		control.executeCommand(editDesTask3);

		SomedayView today = new SomedayView();
		String actualResult = today.getSomedayList().toString();
		String expectedResult = "[Learn French , Try to go skydiving at least once, Go Skydiving ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 6; i++) {
			control.executeCommand(deleteTask1);
		}
	}
	
	@Test
	public void testMarkingDoneTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addSomedayTask1);

		control.executeCommand(addSomedayTask2);

		control.executeCommand(addSomedayTask3);

		control.executeCommand(addSomedayTask4);

		control.executeCommand(markDoneTask2);

		SomedayView someday = new SomedayView();
		String actualResult = someday.getSomedayList().toString();
		String expectedResult = "[Buy belated birthday gifts , Go Skydiving ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 6; i++) {
			control.executeCommand(deleteTask1);
		}
	}
}
