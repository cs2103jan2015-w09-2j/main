//@author A0112715R

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class OneTagTest {
	

	private String addTodayTask1 = "add Submit developer guide by 10am";
	private String addTodayTask2 = "add Lecture from 11am to 1pm";
	private String addTodayTask3 = "add Swimming from 3pm to 4pm";
	
	private String addUpcomingTask1 = "add Birthday celebration from 06 July 2pm to 06 July 7pm";
	private String addUpcomingTask2 = "add Holiday with Family from 08 June 6pm to 10 June 10am";
	
	private String addFloatingTask1 = "add Learn French";
	private String addFloatingTask2 = "add Read \"The Great Gatsby\"";
	
	private String deleteTask1 = "Delete 1";
	
	private String deleteTask3 = "Delete 3";
	private String deleteTask4 = "Delete 4";
	
	private String doneTask1 = "Done 2";
	private String doneTask3 = "Done 3";
	private String editTask1 = "Edit 1 by 1pm";
	private String editTask2 = "Edit 2 CS2103T Lecture";
	private String editTask3 = "edit 3 Swimming at NUS from 6pm to 8pm";
	private String undo = "undo";
	
	private String keyword = "report";
	
	private String addSearchTask1 = "add Submit report by 11pm\"";
	private String addSearchTask2 = "add Write section 1 to section 3 of report from 19 June 3pm to 20 June 5pm\"";
	private String addSearchTask3 = "add Edit the report";

	@Before
	public void beforeTesting(){
		File file = new File("oneTag.json");
		File file2 = new File("config.json");
		file.delete();
		file2.delete();
		ArrayList<Task> arrayList = new ArrayList<Task>();
		Data data = Data.getInstance();
		data.set(arrayList);
	}
	
	@After
	public void clearAfterTesting() throws InterruptedException{
		UserInterface UI = UserInterface.getInstance();
		assertNotNull(UI);
		UI.executeInterface();
		UI.initialize();
		
		File file = new File("oneTag.json");
		File file2 = new File("config.json");
		file.delete();
		file2.delete();
		ArrayList<Task> arrayList = new ArrayList<Task>();
		Data data = Data.getInstance();
		data.set(arrayList);
		
		
	}
	
	//testing the adding of different types of tasks. i.e.timed task, deadline task, floating tasks
	@Test
	public void addTask() throws BadLocationException, InterruptedException {
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
		public void deleteTask() throws BadLocationException, InterruptedException {
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
		public void markTask() throws BadLocationException, InterruptedException {
			UserInterface UI = UserInterface.getInstance();
			Display display = Display.getInstance();
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
			display.getView().update();
			completed = new CompletedView();
			actualResult = completed.getCompletedList().toString();
			expectedResult = "[[11:00 - 13:00] Lecture , [14:00 - 19:00] Birthday celebration ]";
			assertEquals(actualResult,expectedResult);
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(doneTask3);
			UI.setCommand();
			display.getView().update();
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
		public void searchTask() throws BadLocationException, InterruptedException {
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
		
		//testing the editing of tasks
		@Test
		public void editTask() throws BadLocationException, InterruptedException {
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
			input.setText(addTodayTask3);
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
			input.setText(editTask1);	
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(editTask2);
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(editTask3);
			UI.setCommand();
			
			HomeView home = new HomeView();
			String actualResult = home.getList().toString();
			String expectedResult = "[[13:00] Submit developer guide , [11:00 - 13:00] CS2103T Lecture, [18:00 - 20:00] Swimming at NUS, [18:00 - 10:00] Holiday with Family , [14:00 - 19:00] Birthday celebration , Learn French , Read \"The Great Gatsby\" ]";
			assertEquals(actualResult,expectedResult);
			
			//clear the tasks from file for next testing
			for(int i =0; i<6; i++){
				input = UserInterface.commandFromUser;
				assertNotNull(input);
				input.setText(deleteTask1);
				UI.setCommand();
			}	
		}
		
		@Test
		public void undoAddingTask() throws BadLocationException, InterruptedException {
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
			input.setText(undo);
			UI.setCommand();
			
			
			HomeView home = new HomeView();
			String actualResult = home.getList().toString();
			String expectedResult = "[[10:00] Submit developer guide , [14:00 - 19:00] Birthday celebration ]";
			assertEquals(actualResult,expectedResult);
			
			//clear the tasks from file for next testing
			for(int i =0; i<6; i++){
				input = UserInterface.commandFromUser;
				assertNotNull(input);
				input.setText(deleteTask1);
				UI.setCommand();
			}	
		}
		
		@Test
		public void undoDeletingTask() throws BadLocationException, InterruptedException {
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
			input.setText(addFloatingTask2);	
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(deleteTask1);
			UI.setCommand();
			
			input = UserInterface.commandFromUser;
			assertNotNull(input);
			input.setText(undo);
			UI.setCommand();
			
			
			HomeView home = new HomeView();
			String actualResult = home.getList().toString();
			String expectedResult = "[[10:00] Submit developer guide , [14:00 - 19:00] Birthday celebration , Read \"The Great Gatsby\" ]";
			assertEquals(actualResult,expectedResult);
			
			//clear the tasks from file for next testing
			for(int i =0; i<6; i++){
				input = UserInterface.commandFromUser;
				assertNotNull(input);
				input.setText(deleteTask1);
				UI.setCommand();
			}	
		}
		
}
