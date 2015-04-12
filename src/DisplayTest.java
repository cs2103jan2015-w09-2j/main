import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

//@author A0111867A
public class DisplayTest {
	
	protected static final String ADD_TASK_MESSAGE = "Added <font color=\"#CC3300\"><i>%1$s</i></font> to <font color=\"#CC3300\"><i>%2$s</i></font>";
	protected static final String UNDO_ADD_MESSAGE = "Undo action: <font color=\"#CC3300\"><i>\"Add %1$s\"</i></font>";
	
	Display display;
	HomeView homeView;
	TodayView todayView;
	
	@Before
	public void setUp() throws Exception {
		display = Display.getInstance();
		display.set(ADD_TASK_MESSAGE, -1, COMMAND_TYPE.HOME);
		homeView = new HomeView();
		todayView = new TodayView();
		
	}
	
	@Test
	public void testGetView() {
		assertEquals(display.getView().getList(), homeView.getList());
	}
	
	@Test
	public void testGetMessage() {
		assertEquals(ADD_TASK_MESSAGE, display.getMessage());
	}
	
	@Test
	public void testGetViewIndex() {
		assertEquals(display.getViewIndex(), -1);
	}
	
	@Test
	public void testGetCommand() {
		assertEquals(COMMAND_TYPE.HOME, display.getCommand());
	}
	
	@Test
	public void testGetPaging() {
		assertEquals(display.getPaging(), 1);
	}
	
	@Test
	public void testSetView() {
		display.set(todayView);
		assertEquals("test view of set view", display.getView().getClass(), todayView.getClass());
		
		display.set(homeView, 2);
		assertEquals("test view of set view and pageing", display.getView().getClass(), homeView.getClass());
		assertEquals("test paging of set view and paging", display.getPaging(), 2);
		
		display.set(ADD_TASK_MESSAGE);
		assertEquals("test message of set message", display.getMessage(), ADD_TASK_MESSAGE);
		
		display.set(UNDO_ADD_MESSAGE, COMMAND_TYPE.UNDO);
		assertEquals("test message of set message and command", display.getMessage(), UNDO_ADD_MESSAGE);
		assertEquals("test command of set message and command", display.getCommand(), COMMAND_TYPE.UNDO);
	}

}
