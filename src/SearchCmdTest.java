import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

//@author A0111867A
public class SearchCmdTest {

	private static final String TASK_GO_HIKING = "Go Hiking";
	
	private final static String USER_QUERY_ONE = "hiking";
	private final static String USER_QUERY_TWO = "orange";
	Display display;
	Data data;
	
	SearchCmd searchCmd, sameSearchCmd, anotherSearchCmd;
	Task task;
	ArrayList<Task> list, emptyList;
	SearchView searchView;
	HomeView homeView;
	
	@Before
	public void setUp() throws Exception {
		display = Display.getInstance();
		data = Data.getInstance();
		
		task = new Task(TASK_GO_HIKING);
		
		searchCmd = new SearchCmd(USER_QUERY_ONE);
		sameSearchCmd = new SearchCmd(USER_QUERY_ONE);
		anotherSearchCmd = new SearchCmd(USER_QUERY_TWO);
		
		list = new ArrayList<Task>();
		emptyList = new ArrayList<Task>();
		
		list.add(task);
		
		homeView = new HomeView();
		searchView = new SearchView(USER_QUERY_ONE);
		
		data.set(list);
		display.set("");
	}
	
	@Test
	public void testExecute() {
		searchCmd.execute();
		assertEquals(list, display.getView().getList());
	}

	@Test
	public void testUndo() {
		display.set(homeView);
		searchCmd.execute();
		searchCmd.undo();
		assertEquals(homeView.getClass(), display.getView().getClass());
	}
	
	
	@Test
	public void testEquals() {
		assertTrue("same cmd", searchCmd.equals(sameSearchCmd));
		assertFalse("different cmd", searchCmd.equals(anotherSearchCmd));
	}
	
}
