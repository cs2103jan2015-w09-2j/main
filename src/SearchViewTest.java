import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SearchViewTest {
	
	private String addTodayTask1 = "add Essay by 10am";
	private String addTodayTask2 = "add Maths Lecture from 11am to 1pm";
	
	private String addSearchTask1 = "add Submit report by 11pm\"";
	private String addSearchTask2 = "add Write section 1 to section 3 of report from 19 June 3pm to 20 June 5pm\"";
	private String addSearchTask3 = "add Edit the report";
	
	private String addUpcomingTask1 = "add Submit assignment by June 15 6pm";
	private String addUpcomingTask2 = "add Holiday with family in Rome from 11am 17 June to 1pm 27 June";
	
	private String addSomedayTask1 = "add Buy belated birthday gifts";
	private String addSomedayTask2 = "add Go Skydiving";
	
	private String searchKeyword = "report";
	
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
	public void testSearchTasks() {
		Controller control = Controller.getInstance();
		control.executeCommand(addTodayTask1);
		control.executeCommand(addTodayTask2);

		control.executeCommand(addSearchTask1);
		control.executeCommand(addSearchTask2);
		control.executeCommand(addSearchTask3);

		control.executeCommand(addSomedayTask1);
		control.executeCommand(addSomedayTask2);

		control.executeCommand(addUpcomingTask1);
		control.executeCommand(addUpcomingTask2);
		
		SearchView search = new SearchView(searchKeyword);
		String actualResult = search.getSearchList().toString();
		String expectedResult = "[[23:00] Submit report , [15:00 - 17:00] Write section 1 to section 3 of report , Edit the report ]";
		assertEquals(actualResult, expectedResult);

		// clear the tasks from file for next testing
		for (int i = 0; i < 10; i++) {
			control.executeCommand(deleteTask1);
		}
	}

}
