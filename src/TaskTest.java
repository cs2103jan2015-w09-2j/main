

//@author A0111217Y
/**
 * Assumption: time and date validity are handled by OneTagParser class
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;


public class TaskTest {
	
	private static final String EMPTY_STRING = "";
	private static final String MESSAGE_TEST_COMPLETED_TASK = "test completed task";
	private static final String MESSAGE_TEST_TOMORROW_TASK = "test tomorrow task";
	private static final String MESSAGE_TEST_TODAY_TASK = "test today task";
	private static final String DATE_END_EARLY_TIMED_TASK = "2015-03-10T19:30";
	private static final String DATE_END_EARLY_DEADLINE_TASK = "2015-03-10T21:30";
	private static final String DATE_START_EARLY_TIMED_TASK = "2015-03-10T19:00";
	private static final String MESSAGE_TEST_DEADLINE_TASK = "test deadline task";
	private static final String MESSAGE_TEST_TIMED_TASK = "test timed task";
	private static final String MESSAGE_TEST_FLOATING_TASK = "test floating task";
	private static final String MESSAGE_TEST_FLOATING_TIMED_TASK = "test floating task with timed task";
	private static final String MESSAGE_TEST_TIMED_DEADLINE_TASK = "test timed task with deadline task";
	private static final String MESSAGE_TEST_DEADLINE_FLOATING_TASK = "test deadline task with floating task";
	private static final String MESSAGE_TEST_EMPTY_TASK = "test task with empty description";
	private static final String MESSAGE_TEST_NULL_TASK = "test null task";
	private static final String TASK_GO_HIKING = "Go Hiking";
	private static final String TASK_BUY_APPLE = "Buy apple";
	private static final String TASK_BUY_ORANGE = "Buy orange";
	private static final String TASK_GO_RUNNING = "Go running";
	private static final String STRING_EARLY_TIMED_TASK = "[19:00 - 19:30] " + TASK_GO_RUNNING;
	private static final String TASK_GO_HOME = "Go Home";
	private static final String TASK_GO_RT = "Go RT";
	private static final String STRING_EARLY_DEADLINE_TASK = "[21:30] " + TASK_GO_RT;
	private static final String TASK_MOP_FLOOR = "Mop floor";
	private static final int oneDay = 1;
	private static final String EMPTY_SPACE_STRING = "　　" ;
	
	private Task floatingTask;
	private Task dupFloatingTask;
	private Task earlyTimedTask;
	private Task lateTimedTask;
	private Task dupLateTimedTask;
	private Task earlyDeadlineTask;
	private Task lateDeadlineTask;
	private Task dupLateDeadlineTask;
	private Task todayTask;
	private Task tomorrowTask;
	private Task completedFloatingTask;
	private Task completedEarlyTimedTask;
	private Task completedEarlyDeadlineTask;
	private Task completedTodayTask;
	private Task completedTomorrowTask;
	private Task dupCompletedEarlyDeadlineTask;
	private Task nullTask;
	private Task emptyStringFloatingTask;
	private LocalDateTime today;
	private LocalDateTime tomorrow;
	private Task nullReferenceTask = null;
	private Task undefinedTask;
	private Integer integer;

	@Before
	public void setUp() throws Exception {
		today = LocalDateTime.now();
		tomorrow = LocalDateTime.now().plusDays(oneDay);
		todayTask = new Task(today, TASK_GO_HIKING);
		tomorrowTask = new Task(tomorrow, TASK_BUY_APPLE);
		floatingTask = new Task(TASK_BUY_ORANGE);
		dupFloatingTask = new Task(TASK_BUY_ORANGE);
		earlyTimedTask = new Task(LocalDateTime.of(2015, 3, 10, 19, 0), LocalDateTime.of(2015, 3, 10, 19, 30), TASK_GO_RUNNING);
		lateTimedTask = new Task(LocalDateTime.of(2015, 3, 10, 20, 0), LocalDateTime.of(2015, 3, 10, 20, 30), TASK_GO_HOME);
		dupLateTimedTask = new Task(LocalDateTime.of(2015, 3, 10, 20, 0) ,LocalDateTime.of(2015, 3, 10, 20, 30), TASK_GO_HOME);
		earlyDeadlineTask = new Task(LocalDateTime.of(2015, 3, 10, 21, 30), TASK_GO_RT);
		lateDeadlineTask = new Task(LocalDateTime.of(2015, 3, 10, 22, 30), TASK_MOP_FLOOR);
		dupLateDeadlineTask = new Task(LocalDateTime.of(2015, 3, 10, 22, 30), TASK_MOP_FLOOR);
		completedFloatingTask = new Task(TASK_BUY_ORANGE);
		completedFloatingTask.setIsCompleted(true);
		completedEarlyTimedTask = new Task(LocalDateTime.of(2015, 3, 10, 19, 0),LocalDateTime.of(2015, 3, 10, 19, 30), TASK_GO_RUNNING);
		completedEarlyTimedTask.setIsCompleted(true);
		completedEarlyDeadlineTask = new Task(LocalDateTime.of(2015, 3, 10, 21, 30), TASK_GO_RT);
		completedEarlyDeadlineTask.setIsCompleted(true);
		completedTodayTask = new Task(today, TASK_GO_HIKING);
		completedTodayTask.setIsCompleted(true);
		completedTomorrowTask = new Task(tomorrow, TASK_BUY_APPLE);
		completedTomorrowTask.setIsCompleted(true);
		dupCompletedEarlyDeadlineTask = new Task(LocalDateTime.of(2015, 3, 10, 21, 30), TASK_GO_RT);
		dupCompletedEarlyDeadlineTask.setIsCompleted(true);
		nullTask = new Task();
		emptyStringFloatingTask = new Task(EMPTY_SPACE_STRING);
		undefinedTask = new Task();
		undefinedTask.setStart(today);
		undefinedTask.setDescription(TASK_GO_RT);
		integer = new Integer(1);
	}

	@Test
	public void testGetDescription() {
		//test successful getting description
		assertEquals(MESSAGE_TEST_FLOATING_TASK, floatingTask.getDescription(), TASK_BUY_ORANGE);
		//test getting null description
		assertEquals(MESSAGE_TEST_NULL_TASK, nullTask.getDescription(), null);
		//test getting empty string description
		assertEquals(MESSAGE_TEST_EMPTY_TASK, emptyStringFloatingTask.getDescription(), EMPTY_SPACE_STRING);
	}
	
	@Test
	public void testSetDescription(){
		//test getting description after setting any normal string
		floatingTask.setDescription(TASK_GO_HOME);
		assertEquals(MESSAGE_TEST_FLOATING_TASK, floatingTask.getDescription(), TASK_GO_HOME);
		
		//test getting description after setting null string
		floatingTask.setDescription(null);
		assertEquals(MESSAGE_TEST_NULL_TASK, floatingTask.getDescription(), null);
		
		//test getting description after setting empty string
		floatingTask.setDescription(EMPTY_SPACE_STRING);
		assertEquals(MESSAGE_TEST_EMPTY_TASK, floatingTask.getDescription(), EMPTY_SPACE_STRING);
		
	}

	@Test
	public void testGetStart() {
		// Equivalence partition of the 3 supported types of tasks
		assertEquals(MESSAGE_TEST_FLOATING_TASK, floatingTask.getStart(), null);
		assertEquals(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.getStart().toString(), DATE_START_EARLY_TIMED_TASK);
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.getStart(), null);
	}
	
	@Test
	public void testSetStart() {
		//test successful setting of normal date time
		nullTask.setStart(today);
		assertEquals(nullTask.getStart(), today);
		
		//test setting of null for start date time
		nullTask.setStart(null);
		assertEquals(nullTask.getStart(), null);
	}
	
	@Test
	public void testSetEnd(){
		//test successful setting normal date time
		nullTask.setEnd(tomorrow);
		assertEquals(nullTask.getEnd() , tomorrow);
		
		//test setting of null for end date time
		nullTask.setEnd(null);
		assertEquals(nullTask.getEnd(), null);
		
	}
	
	@Test
	public void testGetEnd() {
		// Equivalence partition of the 3 supported types of tasks
		assertEquals(MESSAGE_TEST_FLOATING_TASK, floatingTask.getEnd(), null);
		assertEquals(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.getEnd().toString(), DATE_END_EARLY_TIMED_TASK);
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.getEnd().toString(),DATE_END_EARLY_DEADLINE_TASK);
	}
	
	@Test
	public void testGetIsCompleted(){
		// Equivalence partition of completed and incomplete tasks
		assertFalse(MESSAGE_TEST_COMPLETED_TASK, floatingTask.getIsCompleted());
		assertTrue(MESSAGE_TEST_COMPLETED_TASK, completedEarlyDeadlineTask.getIsCompleted());
	}
	
	@Test
	public void testIsFloatingTask() {
		// Equivalence partition of 3 diferent types of tasks
		assertTrue(MESSAGE_TEST_FLOATING_TASK, floatingTask.isFloatingTask());
		assertFalse(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.isFloatingTask());
		assertFalse(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.isFloatingTask());
		// Check if completed task affects return result
		assertTrue(MESSAGE_TEST_COMPLETED_TASK, completedFloatingTask.isFloatingTask());

	}

	@Test
	public void testIsDeadlineTask() {
		// Equivalence partition of 3 diferent types of tasks
		assertFalse(MESSAGE_TEST_FLOATING_TASK, floatingTask.isDeadlineTask());
		assertFalse(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.isDeadlineTask());
		assertTrue(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.isDeadlineTask());
		// Check if completed task affects return result
		assertTrue(MESSAGE_TEST_COMPLETED_TASK, completedEarlyDeadlineTask.isDeadlineTask());
	}

	@Test
	public void testIsNormalTask() {
		// Equivalence partition of 3 diferent types of tasks
		assertFalse(MESSAGE_TEST_FLOATING_TASK, floatingTask.isNormalTask());
		assertTrue(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.isNormalTask());
		assertFalse(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.isNormalTask());
		// Check if completed task affects return result
		assertTrue(MESSAGE_TEST_COMPLETED_TASK, completedEarlyTimedTask.isNormalTask());
	}
	
	@Test
	public void testIsTodayTask(){
		// Equivalence partition of 3 different types of task for today
		assertTrue(MESSAGE_TEST_TODAY_TASK, todayTask.isTodayTask());
		earlyDeadlineTask.setEnd(today);
		assertTrue(MESSAGE_TEST_TODAY_TASK, earlyDeadlineTask.isTodayTask());
		assertTrue(MESSAGE_TEST_COMPLETED_TASK, completedTodayTask.isTodayTask());
		
		// Check if tasks of any other time will fail
		assertFalse(MESSAGE_TEST_FLOATING_TASK, floatingTask.isTodayTask());
		assertFalse(MESSAGE_TEST_TOMORROW_TASK, tomorrowTask.isTodayTask());
		assertFalse(MESSAGE_TEST_COMPLETED_TASK, completedTomorrowTask.isTodayTask());
		
		// Overdue Task appears under Today
		assertTrue(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.isTodayTask());
		
		// check against null task
		assertFalse(MESSAGE_TEST_NULL_TASK, nullTask.isTodayTask());
		
		// check against undefined task
		assertFalse(undefinedTask.isTodayTask());
	}
	
	@Test
	public void testIsSomedayTask(){
		//Equivalence partition of someday task and not someday task
		assertTrue(MESSAGE_TEST_FLOATING_TASK, floatingTask.isSomedayTask());
		
		//Check if tasks of any other type will return the false
		assertFalse(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.isSomedayTask());
		assertFalse(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.isSomedayTask());
		assertFalse(MESSAGE_TEST_TODAY_TASK, todayTask.isSomedayTask());
		assertFalse(MESSAGE_TEST_TOMORROW_TASK, tomorrowTask.isSomedayTask());
		assertFalse(MESSAGE_TEST_COMPLETED_TASK, completedTodayTask.isSomedayTask());
		assertFalse(MESSAGE_TEST_COMPLETED_TASK, completedTomorrowTask.isSomedayTask());
	}
	
	@Test
	public void testIsUpcomingTask(){
		assertFalse(MESSAGE_TEST_FLOATING_TASK, floatingTask.isUpcomingTask());
		assertFalse(MESSAGE_TEST_TODAY_TASK, todayTask.isUpcomingTask());
		assertTrue(MESSAGE_TEST_TOMORROW_TASK, tomorrowTask.isUpcomingTask());
		assertFalse(MESSAGE_TEST_COMPLETED_TASK, completedTodayTask.isUpcomingTask());
		assertFalse(MESSAGE_TEST_COMPLETED_TASK, completedTomorrowTask.isUpcomingTask());
		
	}
	
	@Test
	public void testToString() {
		// Different format for the 3 different types of task
		assertEquals(MESSAGE_TEST_FLOATING_TASK, floatingTask.toString(), TASK_BUY_ORANGE);
		assertEquals(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.toString(), STRING_EARLY_TIMED_TASK);
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.toString(), STRING_EARLY_DEADLINE_TASK);
		
		// Task with only start time - not used in our system
		assertEquals(undefinedTask.toString(), EMPTY_STRING);
	}

	@Test
	public void testEqualsObject() {
		assertFalse(MESSAGE_TEST_FLOATING_TIMED_TASK, floatingTask.equals(earlyTimedTask));
		assertFalse(MESSAGE_TEST_TIMED_DEADLINE_TASK, earlyTimedTask.equals(earlyDeadlineTask));
		assertFalse(MESSAGE_TEST_DEADLINE_FLOATING_TASK, earlyDeadlineTask.equals(floatingTask));
		assertTrue(MESSAGE_TEST_FLOATING_TASK, floatingTask.equals(floatingTask));
		assertTrue(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.equals(earlyDeadlineTask));
		assertFalse(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.equals(lateDeadlineTask));
		assertTrue(MESSAGE_TEST_TIMED_TASK, lateTimedTask.equals(lateTimedTask));
		assertFalse(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.equals(lateTimedTask));
		assertTrue(MESSAGE_TEST_TIMED_TASK, dupLateTimedTask.equals(lateTimedTask));
		assertTrue(MESSAGE_TEST_DEADLINE_TASK, dupLateDeadlineTask.equals(lateDeadlineTask));
		assertTrue(MESSAGE_TEST_FLOATING_TASK, dupFloatingTask.equals(floatingTask));
		//Additional completed task because a completed task is not equals to a non completed task with the same description and time
		assertFalse(MESSAGE_TEST_COMPLETED_TASK, completedEarlyDeadlineTask.equals(earlyDeadlineTask));
		assertFalse(MESSAGE_TEST_COMPLETED_TASK, completedTodayTask.equals(todayTask));
		assertTrue(MESSAGE_TEST_COMPLETED_TASK, dupCompletedEarlyDeadlineTask.equals(completedEarlyDeadlineTask));
		
		//test something equals null reference Task
		assertFalse(floatingTask.equals(nullReferenceTask));
		
		//test whether other class equals Task class
		assertFalse(floatingTask.equals(integer));
	}
	
	/*
	@Test
	public void testUpdate(){
		//Timed task updating
		earlyTimedTask.update(lateTimedTask);
		assertTrue(earlyTimedTask.equals(lateTimedTask));
		
		//null task updating does not do anything
		floatingTask.update(nullTask);
		assertTrue(floatingTask.equals(floatingTask));
		
		
		
	}
	*/
	

}
