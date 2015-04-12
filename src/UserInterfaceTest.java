//@author A0112715R

import static org.junit.Assert.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserInterfaceTest {

	private String addTodayTask1 = "add Submit developer guide by 10am";
	private String addTodayTask2 = "add Lecture from 11am to 1pm";
	
	private String addUpcomingTask1 = "add Birthday celebration from 06 July 2pm to 06 July 7pm";
	private String addUpcomingTask2 = "add Holiday with Family from 08 June 6pm to 10 June 10am";
	
	private String addFloatingTask1 = "add Learn French";
	private String addFloatingTask2 = "add Read \"The Great Gatsby\"";
	
	private String deleteTask1 = "Delete 1";
	
	private String deleteTask3 = "Delete 3";
	private String deleteTask4 = "Delete 4";
	
	private String doneTask1 = "Done 2";
	private String doneTask3 = "Done 3";
	
	private String editTaskDes = "";
	private String editTaskEndTime = "";
	private String editTaskStartTime = "";
	
	private String editTaskEndDate = "";
	private String editTaskStartDate = "";
	
	private String keyword = "report";
	
	private String addSearchTask1 = "add Submit report by 11pm\"";
	private String addSearchTask2 = "add Write section 1 to section 3 of report from 19 June 3pm to 20 June 5pm\"";
	private String addSearchTask3 = "add Edit the report";

	//testing the adding of different types of tasks. i.e.timed task, deadline task, floating task
	//testing the adding of different types of tasks. i.e.timed task, deadline task, floating task
	@Before
	public void beforeTesting(){
		File file = new File("oneTag.json");
		file.delete();
	}
	
	@After
	public void clearAfterTesting(){
		File file = new File("oneTag.json");
		file.delete();
	}
	
	//testing the adding of different types of tasks. i.e.timed task, deadline task, floating tasks
	@Test
	public void addTask() throws BadLocationException {
		UserInterface UI = UserInterface.getInstance();
		assertNotNull(UI);
		UI.executeInterface();
		UI.initialize();
		
		JTextPane input = UserInterface.commandFromUser;
		assertNotNull(input);
		input.setText(addTodayTask1);
		UI.setCommand();
		
		input = UserInterface.commandFromUser;
		assertNotNull(input);
		input.setText(addTodayTask2);
		UI.setCommand();
		
		input = UserInterface.commandFromUser;
		assertNotNull(input);
		input.setText(addUpcomingTask1);
		UI.setCommand();
			
		input = UserInterface.commandFromUser;
		assertNotNull(input);
		input.setText(addUpcomingTask2);	
		UI.setCommand();
		
		input = UserInterface.commandFromUser;
		assertNotNull(input);
		input.setText(addFloatingTask1);	
		UI.setCommand();
		
		input = UserInterface.commandFromUser;
		assertNotNull(input);
		input.setText(addFloatingTask2);
		UI.setCommand();
		
		
		HomeView home = new HomeView();
		String actualResult = home.getList().toString();
		String expectedResult = "[[10:00] Submit developer guide , [11:00 - 13:00] Lecture , [18:00 - 10:00] Holiday with Family , [14:00 - 19:00] Birthday celebration , Learn French , Read \"The Great Gatsby\" ]";
		assertEquals(actualResult,expectedResult);
		
		//clear the tasks from file for next testing
		for(int i =0; i<6; i++){
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(deleteTask1);
			UI.setCommand();
		}	
	}
	
	//testing deleting tasks
		@Test
		public void deleteTask() throws BadLocationException {
			UserInterface UI = UserInterface.getInstance();
			assertNotNull(UI);
			UI.executeInterface();
			UI.initialize();
			
			JTextPane input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addTodayTask1);
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addTodayTask2);
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addUpcomingTask1);
			UI.setCommand();
				
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addUpcomingTask2);	
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addFloatingTask1);	
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addFloatingTask2);
			UI.setCommand();
			
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(deleteTask1);
			UI.setCommand();
			HomeView home = new HomeView();
			String actualResult = home.getList().toString();
			String expectedResult = "[[11:00 - 13:00] Lecture , [18:00 - 10:00] Holiday with Family , [14:00 - 19:00] Birthday celebration , Learn French , Read \"The Great Gatsby\" ]";
			assertEquals(actualResult,expectedResult);
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(deleteTask3);
			UI.setCommand();
			home = new HomeView();
			actualResult = home.getList().toString();
			expectedResult = "[[11:00 - 13:00] Lecture , [18:00 - 10:00] Holiday with Family , Learn French , Read \"The Great Gatsby\" ]";
			assertEquals(actualResult,expectedResult);
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(deleteTask4);
			UI.setCommand();
			home = new HomeView();
			actualResult = home.getList().toString();
			expectedResult = "[[11:00 - 13:00] Lecture , [18:00 - 10:00] Holiday with Family , Learn French ]";
			assertEquals(actualResult,expectedResult);
			
			
			//clear the tasks from file for next testing
			for(int i =0; i<6; i++){
				input = UserInterface.commandFromUser;
				assertNotNull(input);
				input.setText(deleteTask1);
				UI.setCommand();
			}	
		}
		
//		testing marking task as done
		@Test
		public void markTask() throws BadLocationException {
			UserInterface UI = UserInterface.getInstance();
			assertNotNull(UI);
			UI.executeInterface();
			UI.initialize();
			
			JTextPane input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addTodayTask1);
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addTodayTask2);
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addUpcomingTask1);
			UI.setCommand();
				
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addUpcomingTask2);	
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addFloatingTask1);	
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addFloatingTask2);
			UI.setCommand();
			
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(doneTask1);
			UI.setCommand();
			CompletedView completed = new CompletedView();
			String actualResult = completed.getCompletedList().toString();
			String expectedResult = "[[11:00 - 13:00] Lecture ]";
			assertEquals(actualResult,expectedResult);
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(doneTask3);
			UI.setCommand();
			completed = new CompletedView();
			actualResult = completed.getCompletedList().toString();
			expectedResult = "[[11:00 - 13:00] Lecture , [14:00 - 19:00] Birthday celebration ]";
			assertEquals(actualResult,expectedResult);
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(doneTask3);
			UI.setCommand();
			completed = new CompletedView();
			actualResult = completed.getCompletedList().toString();
			expectedResult = "[[11:00 - 13:00] Lecture , [14:00 - 19:00] Birthday celebration , Learn French ]";
			assertEquals(actualResult,expectedResult);
			
			//clear the tasks from file for next testing
			for(int i =0; i<6; i++){
				input = UserInterface.commandFromUser;
				assertNotNull(input);
				input.setText(deleteTask1);
				UI.setCommand();
			}	
		}
		
//		search for the keyword "report"
		@Test
		public void searchTask() throws BadLocationException {
			UserInterface UI = UserInterface.getInstance();
			assertNotNull(UI);
			UI.executeInterface();
			UI.initialize();
			
			JTextPane input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addTodayTask1);
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addUpcomingTask1);
			UI.setCommand();	
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addFloatingTask1);	
			UI.setCommand();

			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addSearchTask1);	
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addSearchTask2);	
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(addSearchTask3);
			UI.setCommand();
			
			SearchView search = new SearchView(keyword);		
			String actualResult = search.getList().toString();
			String expectedResult = "[[23:00] Submit report , [15:00 - 17:00] Write section 1 to section 3 of report , Edit the report ]";
			assertEquals(actualResult,expectedResult);
			
			//clear the tasks from file for next testing
			for(int i =0; i<10; i++){
				input = UserInterface.commandFromUser;
				assertNotNull(input);
				input.setText(deleteTask1);
				UI.setCommand();
			}	
		}
		
	
}
