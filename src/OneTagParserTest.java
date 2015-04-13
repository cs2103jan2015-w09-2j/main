//@author A0108436H
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class OneTagParserTest {
	private static final int TEST_INDEX = 1;
	private static final String ADD_FLOATING ="add 123";
	private static final String ADD_TIMED = "add meet with Mr.Tom from 12pm to 1pm";
	private static final String ADD_DEADLINE = "add meet with Mr.Tom by tomorrow";
	private static final String EDIT_SOMEDAY = "edit 1 someday";
	private static final String EDIT_TASK_DESCRIPTION = "edit 1 meet with Mr.Tom";
	private static final String EDIT_TD_SOMEDAY = "edit 1 meet with Mr.Tom someday";
	private static final String EDIT_TIME_1 = "edit 1 from 12pm";
	private static final String EDIT_TIME_2 = "edit 1 to 12pm";
	private static final String EDIT_TIME_3 = "edit 1 by 12pm";
	private static final String EDIT_START_END = "edit 1 from 12pm to 1pm";
	private static final String EDIT_TD_TIME_1 = "edit 1 meet with Mr.Tom from 12pm";
	private static final String EDIT_TD_TIME_2 = "edit 1 meet with Mr.Tom to 12pm";
	private static final String EDIT_TD_TIME_3 = "edit 1 meet with Mr.Tom by 12pm";
	private static final String EDIT_TD_TIME = "edit 1 meet with Mr.Tom from 12pm to 1pm";
	private static final String UNDO = "undo";
	private static final String HOME = "home";
	private static final String UPCOMING_ONE = "upcoming 1";
	private static final String DELETE = "delete 1";
	private static final String SAVE = "save D://";
	private static final String DONE = "done 1";
	private static final String SEARCH = "search tom";
	
	private AddCmd addCmd_FLOATING;
	private AddCmd addCmd_TIMED;
	private AddCmd addCmd_DEADLINE;
	
	private EditCmd editCmd_SOMEDAY;
	private EditCmd editCmd_TASK_DESCRIPTION;
	private EditCmd editCmd_TD_SOMEDAY;
	private EditCmd editCmd_TIME_1;
	private EditCmd editCmd_TIME_2;
	private EditCmd editCmd_TIME_3;
	private EditCmd editCmd_START_END;
	private EditCmd editCmd_TD_TIME_1;
	private EditCmd editCmd_TD_TIME_2;
	private EditCmd editCmd_TD_TIME_3;
	private EditCmd editCmd_TD_TIME;
	
	private UndoCmd undo;
	
	private ViewCmd view;
	private ViewCmd view_one;
	
	private SaveCmd save;
	
	private DeleteCmd delete; 
	
	private CompletedCmd done;
	
	private SearchCmd search;
	
	private Task task_FLOATING;
	private Task task_TIMED;
	private Task task_DEADLINE;
	
	private OneTagParser testParser_add_FLOATING = new OneTagParser(ADD_FLOATING);
	private OneTagParser testParser_add_TIMED = new OneTagParser(ADD_TIMED);
	private OneTagParser testParser_add_DEADLINE = new OneTagParser(ADD_DEADLINE);
	private OneTagParser testParser_edit_SOMEDAY = new OneTagParser(EDIT_SOMEDAY);
	private OneTagParser testParser_edit_TASK_DESCRIPTION = new OneTagParser(EDIT_TASK_DESCRIPTION);
	private OneTagParser testParser_edit_TD_SOMEDAY = new OneTagParser(EDIT_TD_SOMEDAY);
	private OneTagParser testParser_edit_TIME_1 = new OneTagParser(EDIT_TIME_1);
	private OneTagParser testParser_edit_TIME_2 = new OneTagParser(EDIT_TIME_2);
	private OneTagParser testParser_edit_TIME_3 = new OneTagParser(EDIT_TIME_3);
	private OneTagParser testParser_edit_START_END = new OneTagParser(EDIT_START_END);
	private OneTagParser testParser_edit_TD_TIME_1 = new OneTagParser(EDIT_TD_TIME_1);
	private OneTagParser testParser_edit_TD_TIME_2 = new OneTagParser(EDIT_TD_TIME_2);
	private OneTagParser testParser_edit_TD_TIME_3 = new OneTagParser(EDIT_TD_TIME_3);
	private OneTagParser testParser_edit_TD_TIME = new OneTagParser(EDIT_TD_TIME);
	private OneTagParser testParser_undo = new OneTagParser(UNDO);
	private OneTagParser testParser_view = new OneTagParser(HOME);
	private OneTagParser testParser_view_one = new OneTagParser(UPCOMING_ONE);
	private OneTagParser testParser_delete = new OneTagParser(DELETE);
	private OneTagParser testParser_save = new OneTagParser(SAVE);
	private OneTagParser testParser_done = new OneTagParser(DONE);
	private OneTagParser testParser_search = new OneTagParser(SEARCH);
//	public Task(LocalDateTime end, String description);
//	public Task(String taskDescription);
//	public Task(LocalDateTime start, LocalDateTime end, String description);
	@Before
	public void initialize() {
		task_FLOATING = new Task("123");
		addCmd_FLOATING = new AddCmd(task_FLOATING);
		task_TIMED = new Task(testParser_add_TIMED.parseDate("26 April 2015"),testParser_add_TIMED.parseDate("27 April 2015"), "meet with Mr.Tom");
		task_DEADLINE = new Task(testParser_add_DEADLINE.parseDate("tomorrow"),"meet with Mr.Tom");
		
		addCmd_FLOATING = new AddCmd(task_FLOATING);
		addCmd_TIMED = new AddCmd(task_TIMED);
		addCmd_DEADLINE = new AddCmd(task_DEADLINE);
		
		editCmd_SOMEDAY = new EditCmd(TEST_INDEX, true);
		editCmd_TASK_DESCRIPTION = new EditCmd(TEST_INDEX,"meet with Mr.Tom");
		editCmd_TD_SOMEDAY = new EditCmd(TEST_INDEX,"meet with Mr.Tom",true);
		editCmd_TIME_1 = new EditCmd(TEST_INDEX,testParser_edit_TIME_1.parseDate("12pm"),1);
		editCmd_TIME_2 = new EditCmd(TEST_INDEX,testParser_edit_TIME_2.parseDate("12pm"),2);
		editCmd_TIME_3 = new EditCmd(TEST_INDEX,testParser_edit_TIME_3.parseDate("12pm"),3);
		editCmd_START_END = new EditCmd(TEST_INDEX,testParser_edit_START_END.parseDate("12pm"),testParser_edit_START_END.parseDate("1pm"));
		editCmd_TD_TIME_1 = new EditCmd(TEST_INDEX,"meet with Mr.Tom", testParser_edit_TD_TIME_1.parseDate("12pm"),1);
		editCmd_TD_TIME_2 = new EditCmd(TEST_INDEX, "meet with Mr.Tom", testParser_edit_TD_TIME_2.parseDate("12pm"),2);
		editCmd_TD_TIME_3 = new EditCmd(TEST_INDEX,"meet with Mr.Tom", testParser_edit_TD_TIME_3.parseDate("12pm"),3);
		editCmd_TD_TIME = new EditCmd(TEST_INDEX,"meet with Mr.Tom", testParser_edit_TD_TIME.parseDate("12pm"),testParser_edit_TD_TIME.parseDate("1pm"));	
		
		undo = new UndoCmd();
		view = new ViewCmd(OneTagParser.getCommand(HOME));
		view_one = new ViewCmd(OneTagParser.getCommand("upcoming"),1); 
		
		delete = new DeleteCmd(TEST_INDEX);
		
		save = new SaveCmd("D://");
		
		done = new CompletedCmd(TEST_INDEX);
		
		search = new SearchCmd("tom");
	}
	
	@Test
	public void test() {
		assertFalse(addCmd_FLOATING.equals(testParser_add_FLOATING.toCmd()));
		assertFalse(addCmd_TIMED.equals(testParser_add_TIMED.toCmd()));
		assertFalse(addCmd_DEADLINE.equals(testParser_add_DEADLINE.toCmd()));
	//	assertTrue(editCmd_SOMEDAY.equals(testParser_edit_SOMEDAY.toCmd()));
// 		assertFalse(editCmd_TASK_DESCRIPTION.equals(testParser_edit_TASK_DESCRIPTION.toCmd()));
		assertFalse(editCmd_TD_SOMEDAY.equals(testParser_edit_TD_SOMEDAY.toCmd()));
	//	assertFalse(editCmd_TIME_1.equals(testParser_edit_TIME_1.toCmd()));
		assertFalse(editCmd_TD_TIME_2.equals(testParser_edit_TIME_2.toCmd()));
		assertFalse(editCmd_TD_TIME_3.equals(testParser_edit_TIME_3.toCmd()));
	
	//	assertTrue(editCmd_TD_TIME.equals(testParser_edit_TD_TIME.toCmd()));	
		assertTrue(view_one.equals(testParser_view_one.toCmd()));
		assertTrue(search.equals(testParser_search.toCmd()));
		assertTrue(delete.equals(testParser_delete.toCmd()));
		assertTrue(view.equals(testParser_view.toCmd()));
		assertTrue(undo.equals(testParser_undo.toCmd()));
		assertTrue(done.equals(testParser_done.toCmd()));
	//	assertTrue(save.equals(testParser_save.toCmd()));
	}
}
