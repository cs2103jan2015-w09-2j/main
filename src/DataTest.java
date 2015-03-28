import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class DataTest {
	
	Data data;
	
	LocalDateTime today;
	LocalDateTime tomorrow;
	
	private static final String MESSAGE_TEST_COMPLETED_TASK = "test completed task";
	private static final String MESSAGE_TEST_TOMORROW_TASK = "test tomorrow task";
	private static final String MESSAGE_TEST_TODAY_TASK = "test today task";
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
	
	private ArrayList<Task> emptyList;
	private ArrayList<Task> oneItemList;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		data = Data.getInstance();

		today = LocalDateTime.now();
		tomorrow = LocalDateTime.now().plusDays(oneDay);
		todayTask = new Task(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), today.getHour(), today.getMinute(), today.getYear(), today.getMonthValue(), today.getDayOfMonth(), today.getHour(), today.getMinute(), TASK_GO_HIKING);
		tomorrowTask = new Task(tomorrow.getYear(), tomorrow.getMonthValue(), tomorrow.getDayOfMonth(), tomorrow.getHour(), tomorrow.getMinute(), TASK_BUY_APPLE);
		floatingTask = new Task(TASK_BUY_ORANGE);
		dupFloatingTask = new Task(TASK_BUY_ORANGE);
		earlyTimedTask = new Task(2015, 3, 10, 19, 0, 2015, 3, 10, 19, 30, TASK_GO_RUNNING);
		lateTimedTask = new Task(2015, 3, 10, 20, 0, 2015, 3, 10, 20, 30, TASK_GO_HOME);
		dupLateTimedTask = new Task(2015, 3, 10, 20, 0, 2015, 3, 10, 20, 30, TASK_GO_HOME);
		earlyDeadlineTask = new Task(2015, 3, 10, 21, 30, TASK_GO_RT);
		lateDeadlineTask = new Task(2015, 3, 10, 22, 30, TASK_MOP_FLOOR);
		dupLateDeadlineTask = new Task(2015, 3, 10, 22, 30, TASK_MOP_FLOOR);
		completedFloatingTask = new Task(TASK_BUY_ORANGE);
		completedFloatingTask.setIsCompleted(true);
		completedEarlyTimedTask = new Task(2015, 3, 10, 19, 0, 2015, 3, 10, 19, 30, TASK_GO_RUNNING);
		completedEarlyTimedTask.setIsCompleted(true);
		completedEarlyDeadlineTask = new Task(2015, 3, 10, 21, 30, TASK_GO_RT);
		completedEarlyDeadlineTask.setIsCompleted(true);
		completedTodayTask = new Task(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), today.getHour(), today.getMinute(), today.getYear(), today.getMonthValue(), today.getDayOfMonth(), today.getHour(), today.getMinute(), TASK_GO_HIKING);
		completedTodayTask.setIsCompleted(true);
		completedTomorrowTask = new Task(tomorrow.getYear(), tomorrow.getMonthValue(), tomorrow.getDayOfMonth(), tomorrow.getHour(), tomorrow.getMinute(), TASK_BUY_APPLE);
		completedTomorrowTask.setIsCompleted(true);
		dupCompletedEarlyDeadlineTask = new Task(2015, 3, 10, 21, 30, TASK_GO_RT);
		dupCompletedEarlyDeadlineTask.setIsCompleted(true);
		
		emptyList = new ArrayList<Task>();
		oneItemList = new ArrayList<Task>();
		oneItemList.add(floatingTask);
	}
	
	@Test
	public void testSet() {
		data.set(emptyList);
		assertEquals("set empty list", data.getData().toString(), "[]");
		 
		data.set(oneItemList);
		assertEquals("set one item list", data.getData().toString(), "[" + floatingTask.toString() + "]");
	}

	@Test
	public void testAdd() {
		data.set(emptyList);

		assertEquals("add one item", data.add(floatingTask), true);
	}
	
	@Test
	public void testRemove() {
		data.set(emptyList);
		data.add(floatingTask);
		
		assertEquals("remove one item", data.remove(floatingTask), true);
	}

	@Test
	public void testGetToday() {
		data.set(new ArrayList<Task>());
		assertEquals("empty list", data.getToday().toString(), "[]");
		
		data.add(todayTask);
		assertEquals("added today task", data.getToday().toString(), "[" + todayTask.toString() + "]");
		
		data.add(tomorrowTask);
		assertEquals("added tomorrow task", data.getToday().toString(), "[" + todayTask.toString() + "]");
		 
		data.add(floatingTask);
		assertEquals("added upcoming task", data.getToday().toString(), "[" + todayTask.toString() + "]");	
	}
	
	@Test
	public void testGetUpcoming() {
		data.set(new ArrayList<Task>());
		
		data.add(todayTask);
		assertEquals("added today task", data.getUpcoming().toString(), "[]");
		
		data.add(tomorrowTask);
		assertEquals("added tomorrow task", data.getUpcoming().toString(), "[" + tomorrowTask.toString() + "]");
		
		data.add(floatingTask);
		assertEquals("added upcoming task", data.getUpcoming().toString(), "[" + tomorrowTask.toString() + "]");	
	}
	
	@Test
	public void testGetSomeday() {
		data.set(new ArrayList<Task>());
		
		data.add(todayTask);
		assertEquals("added today task", data.getSomeday().toString(), "[]");
		
		data.add(tomorrowTask);
		assertEquals("added tomorrow task", data.getSomeday().toString(), "[]");
		
		data.add(floatingTask);
		assertEquals("added upcoming task", data.getSomeday().toString(), "[" + floatingTask.toString() + "]");	
	}

}
