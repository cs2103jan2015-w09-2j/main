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
	
	private static final String TASK_GO_HIKING = "Go Hiking";
	private static final String TASK_BUY_APPLE = "Buy apple";
	private static final String TASK_BUY_ORANGE = "Buy orange";
	private static final String TASK_GO_RUNNING = "Go running";
	private static final String TASK_GO_HOME = "Go Home";
	private static final String TASK_GO_RT = "Go RT";
	private static final int oneDay = 1;
	
	private Task floatingTask;
	private Task todayTask;
	private Task tomorrowTask;
	private Task completedFloatingTask;
	private Task completedTodayTask;
	private Task completedTomorrowTask;
	
	private ArrayList<Task> emptyList;
	private ArrayList<Task> oneItemList;
	private ArrayList<Task> allList;
	
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
		
		todayTask = new Task(LocalDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), today.getHour(), today.getMinute()), LocalDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), today.getHour(), today.getMinute()), TASK_GO_HIKING);
		tomorrowTask = new Task(tomorrow, TASK_BUY_APPLE);
		floatingTask = new Task(TASK_BUY_ORANGE);
		completedTodayTask = new Task(today, TASK_GO_RUNNING);
		completedTodayTask.setIsCompleted(true);
		completedTomorrowTask = new Task(tomorrow, TASK_GO_HOME);
		completedTomorrowTask.setIsCompleted(true);
		completedFloatingTask = new Task(TASK_GO_RT);
		completedFloatingTask.setIsCompleted(true);
		
		emptyList = new ArrayList<Task>();
		
		oneItemList = new ArrayList<Task>();
		oneItemList.add(floatingTask);
		
		allList = new ArrayList<Task>();
		allList.add(todayTask);
		allList.add(tomorrowTask);
		allList.add(floatingTask);
		allList.add(completedTodayTask);
		allList.add(completedTomorrowTask);
		allList.add(completedFloatingTask);
	
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
		assertFalse("empty list", data.remove(floatingTask));
		
		data.add(floatingTask);
		assertTrue("list with one item", data.remove(floatingTask));
	}

	@Test
	public void testGetToday() {
		data.set(allList);
		assertEquals("today and not completed", data.getToday().toString(), "[" + todayTask.toString() + "]");
	}
	
	@Test
	public void testGetUpcoming() {
		data.set(allList);
		assertEquals("upcoming and not completed", data.getUpcoming().toString(), "[" + tomorrowTask.toString() + "]");	
	}
	
	@Test
	public void testGetSomeday() {
		data.set(allList);
		assertEquals("someday and not completed", data.getSomeday().toString(), "[" + floatingTask.toString() + "]");	
	}
	
	@Test
	public void testGetCompleted() {
		data.set(allList);
		assertEquals("completed today, upcoming and someday", data.getCompleted().toString(), 
				"[" + completedTodayTask.toString() + ", "
						+ completedTomorrowTask.toString() + ", "
						+ completedFloatingTask.toString() + "]");	
	}
	
	@Test
	public void testGetSearched() {
		data.set(oneItemList);
		assertEquals("is description", data.getSearched(TASK_BUY_ORANGE), oneItemList);
		assertEquals("is not description", data.getSearched(TASK_GO_RT), emptyList);	
		assertEquals("search upper case", data.getSearched(TASK_BUY_ORANGE.toUpperCase()), oneItemList);
		assertEquals("search lower case", data.getSearched(TASK_BUY_ORANGE.toLowerCase()), oneItemList);
	}
	
}
