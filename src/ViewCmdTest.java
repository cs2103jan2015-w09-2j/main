import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

//@author A0111867A
public class ViewCmdTest {
	
	final static int SECOND_PAGE = 2;
	
	Display display;
	
	ViewCmd viewCmd, sameViewCmd, anotherViewCmd;
	TodayView todayView;
	HomeView homeView;
	
	@Before
	public void setUp() throws Exception {
		display = Display.getInstance();
		
		viewCmd = new ViewCmd(COMMAND_TYPE.TODAY, SECOND_PAGE);
		sameViewCmd = new ViewCmd(COMMAND_TYPE.TODAY, SECOND_PAGE);
		anotherViewCmd = new ViewCmd(COMMAND_TYPE.HOME);
		
		todayView = new TodayView();
		homeView = new HomeView();
	}
	
	@Test
	public void testExecute() {
		viewCmd.execute();
		assertEquals(todayView.getClass(), display.getView().getClass());
		assertEquals("page", SECOND_PAGE, display.getPaging());
	}

	@Test
	public void testUndo() {
		display.set(homeView);
		viewCmd.execute();
		viewCmd.undo();
		assertEquals("view", homeView.getClass(), display.getView().getClass());
		assertEquals("page", SECOND_PAGE, display.getPaging());
	}
	
	
	@Test
	public void testEquals() {
		assertTrue("same cmd", viewCmd.equals(sameViewCmd));
		assertFalse("different cmd", viewCmd.equals(anotherViewCmd));
	}
	
}
