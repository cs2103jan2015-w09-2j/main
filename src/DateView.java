import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class DateView extends View{
	
	private static DateView dateView = null;
	private ArrayList<Task> today;
	private ArrayList<Task> upcoming;
	private ArrayList<Task> someday;
	
	private DateView(){
		this.today = new ArrayList<Task>();
		this.upcoming = new ArrayList<Task>();
		this.someday = new ArrayList<Task>();
	}
	
	private DateView(ArrayList<Task> today, ArrayList<Task> upcoming, ArrayList<Task> someday) {
		this.today = today;
		this.upcoming = upcoming;
		this.someday = someday;
	}
	
	public static DateView getInstance(){
		if(dateView == null){
			dateView = new DateView();
		}
		return dateView;
	}
	
	public static void setInstance(ArrayList<Task> today, ArrayList<Task> upcoming, ArrayList<Task> someday){
		dateView = new DateView(today, upcoming, someday);
	}
	
	protected String getToday() {
		String tasksForToday = getTask(today);
		return tasksForToday;
	}

	protected String getUpcoming() {
		String upcomingTasks = getTask(upcoming);
		return upcomingTasks;
	}

	protected String getSomeday() {
		String tasksForSomeday = getTask(someday);
		return tasksForSomeday;

	}

	protected String getTask(ArrayList<Task> taskArray) {
		String tasks = "";
		int i = 0;
		
		for (Task task : taskArray) {
			i++;
			tasks += i + "." + task.toString() + "\n";
		}
		return tasks;
	}
	
	private void show() throws BadLocationException {
		// TODO Auto-generated method stub
		UserInterface ui = UserInterface.getInstanceOfDisplay();
		JTextPane showToUser = ui.getShowToUser();
		
		StyledDocument doc = showToUser.getStyledDocument();
		Style style = showToUser.addStyle("Style", null);
		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), " Today: \n", style);

		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), getToday(), style);

		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "\n Upcoming: \n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), getUpcoming(), style);

		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "\n Someday: \n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), getSomeday(), style);

	}
	
	public Task getTask(int numbering){
		int index = numbering - 1;
		
		int todaySize = today.size();
		int dateSize = todaySize + upcoming.size();
		int allSize = dateSize + someday.size();
		
		if(index > -1 && index < todaySize){
			return today.get(index);
		}
		else if(index < dateSize){
			return upcoming.get(index);
		}
		else if(index < allSize){
			return someday.get(index);
		}
		else{
			return new Task();
		}
	}
	
}
