import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestTask {
	
	private static final String DATE_END_EARLY_TIMED_TASK = "2015-03-10T19:30";
	private static final String DATE_END_LATE_TIMED_TASK = "2015-03-10T20:30";
	private static final String DATE_END_EARLY_DEADLINE_TASK = "2015-03-10T21:30";
	private static final String DATE_END_LATE_DEADLINE_TASK = "2015-03-10T22:30";
	private static final String DATE_START_LATE_TIMED_TASK = "2015-03-10T20:00";
	private static final String DATE_START_EARLY_TIMED_TASK = "2015-03-10T19:00";
	private static final String MESSAGE_TEST_DEADLINE_TASK = "test deadline task";
	private static final String MESSAGE_TEST_TIMED_TASK = "test timed task";
	private static final String MESSAGE_TEST_FLOATING_TASK = "test floating task";
	private static final String MESSAGE_TEST_FLOATING_TIMED_TASK = "test floating task with timed task";
	private static final String MESSAGE_TEST_TIMED_DEADLINE_TASK = "test timed task with deadline task";
	private static final String MESSAGE_TEST_DEADLINE_FLOATING_TASK = "test deadline task with floating task";
	private static final String TASK_BUY_ORANGE = "Buy orange";
	private static final String TASK_GO_RUNNING = "Go running";
	private static final String STRING_EARLY_TIMED_TASK = "[19:00 - 19:30] " + TASK_GO_RUNNING;
	private static final String TASK_GO_HOME = "Go Home";
	private static final String TASK_GO_RT = "Go RT";
	private static final String STRING_EARLY_DEADLINE_TASK = "[21:30] " + TASK_GO_RT;
	private static final String TASK_MOP_FLOOR = "Mop floor";
	private static final int BEFORE = -1; 
	private static final int EQUAL = 0;
	private static final int AFTER = 1;
	private static final String MESSAGE_TEST_FLOATING_DEADLINE_TASK = "test floating task with deadline task";
	private static final String MESSAGE_TEST_TIMED_FLOATING_TASK = "test timed task with floating task";
	private static final String MESSAGE_TEST_DEADLINE_TIMED_TASK = "test deadline task with timed task";
	
	private Task floatingTask;
	private Task dupFloatingTask;
	private Task earlyTimedTask;
	private Task lateTimedTask;
	private Task dupLateTimedTask;
	private Task earlyDeadlineTask;
	private Task lateDeadlineTask;
	private Task dupLateDeadlineTask;
	private ArrayList<Task> toDo = new ArrayList<Task>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		floatingTask = new Task(TASK_BUY_ORANGE);
		dupFloatingTask = new Task(TASK_BUY_ORANGE);
		earlyTimedTask = new Task(2015, 3, 10, 19, 0, 2015, 3, 10, 19, 30, TASK_GO_RUNNING);
		lateTimedTask = new Task(2015, 3, 10, 20, 0, 2015, 3, 10, 20, 30, TASK_GO_HOME);
		dupLateTimedTask = new Task(2015, 3, 10, 20, 0, 2015, 3, 10, 20, 30, TASK_GO_HOME);
		earlyDeadlineTask = new Task(2015, 3, 10, 21, 30, TASK_GO_RT);
		lateDeadlineTask = new Task(2015, 3, 10, 22, 30, TASK_MOP_FLOOR);
		dupLateDeadlineTask = new Task(2015, 3, 10, 22, 30, TASK_MOP_FLOOR);
		
		toDo.add(floatingTask);
		toDo.add(earlyTimedTask);
		toDo.add(lateTimedTask);
		toDo.add(earlyDeadlineTask);
		toDo.add(lateDeadlineTask);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDescription() {
		assertEquals(MESSAGE_TEST_FLOATING_TASK, floatingTask.getDescription(), TASK_BUY_ORANGE);
		assertEquals(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.getDescription(), TASK_GO_RUNNING);
		assertEquals(MESSAGE_TEST_TIMED_TASK, lateTimedTask.getDescription(), TASK_GO_HOME);
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.getDescription(), TASK_GO_RT);
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, lateDeadlineTask.getDescription(), TASK_MOP_FLOOR);
	}

	@Test
	public void testGetStart() {
		assertEquals(MESSAGE_TEST_FLOATING_TASK, floatingTask.getStart(), null);
		assertEquals(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.getStart().toString(), DATE_START_EARLY_TIMED_TASK);
		assertEquals(MESSAGE_TEST_TIMED_TASK, lateTimedTask.getStart().toString(), DATE_START_LATE_TIMED_TASK);
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.getStart(), null);
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, lateDeadlineTask.getStart(), null);
	}

	@Test
	public void testGetEnd() {
		assertEquals(MESSAGE_TEST_FLOATING_TASK, floatingTask.getEnd(), null);
		assertEquals(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.getEnd().toString(), DATE_END_EARLY_TIMED_TASK);
		assertEquals(MESSAGE_TEST_TIMED_TASK, lateTimedTask.getEnd().toString(), DATE_END_LATE_TIMED_TASK);	
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.getEnd().toString(),DATE_END_EARLY_DEADLINE_TASK);
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, lateDeadlineTask.getEnd().toString(), DATE_END_LATE_DEADLINE_TASK);
	}

	@Test
	public void testIsFloatingTask() {
		assertEquals(MESSAGE_TEST_FLOATING_TASK, floatingTask.isFloatingTask(), true);
		assertEquals(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.isFloatingTask(), false);
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.isFloatingTask(), false);
	}

	@Test
	public void testIsDeadlineTask() {
		assertEquals(MESSAGE_TEST_FLOATING_TASK, floatingTask.isDeadlineTask(), false);
		assertEquals(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.isDeadlineTask(), false);
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.isDeadlineTask(), true);
	}

	@Test
	public void testIsNormalTask() {
		assertEquals(MESSAGE_TEST_FLOATING_TASK, floatingTask.isNormalTask(), false);
		assertEquals(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.isNormalTask(), true);
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.isNormalTask(), false);
	}

	@Test
	public void testToString() {
		assertEquals(MESSAGE_TEST_FLOATING_TASK, floatingTask.toString(), TASK_BUY_ORANGE);
		assertEquals(MESSAGE_TEST_TIMED_TASK, earlyTimedTask.toString(), STRING_EARLY_TIMED_TASK);
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, earlyDeadlineTask.toString(), STRING_EARLY_DEADLINE_TASK);
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
	}

	@Test
	public void testCompareTo() {
		assertEquals(MESSAGE_TEST_FLOATING_TASK, floatingTask.compareTo(dupFloatingTask), EQUAL);
		assertEquals(MESSAGE_TEST_TIMED_TASK, dupLateTimedTask.compareTo(lateTimedTask), EQUAL);
		assertEquals(MESSAGE_TEST_DEADLINE_TASK, dupLateDeadlineTask.compareTo(lateDeadlineTask), EQUAL);
		assertEquals(MESSAGE_TEST_FLOATING_TIMED_TASK, floatingTask.compareTo(earlyTimedTask), BEFORE);
		assertEquals(MESSAGE_TEST_FLOATING_DEADLINE_TASK, floatingTask.compareTo(earlyDeadlineTask), BEFORE);
		assertEquals(MESSAGE_TEST_TIMED_FLOATING_TASK, earlyTimedTask.compareTo(floatingTask), AFTER);
		assertEquals(MESSAGE_TEST_TIMED_DEADLINE_TASK, earlyTimedTask.compareTo(earlyDeadlineTask), AFTER);
		assertEquals(MESSAGE_TEST_DEADLINE_FLOATING_TASK, earlyDeadlineTask.compareTo(floatingTask), AFTER);
		assertEquals(MESSAGE_TEST_DEADLINE_TIMED_TASK, earlyDeadlineTask.compareTo(earlyTimedTask), BEFORE);
	}

}
