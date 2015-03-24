import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class DateView implements View{

	private ArrayList<Task> today;
	private ArrayList<Task> upcoming;
	private ArrayList<Task> someday;
	private int i = 0;
	
	public DateView(){
		update();
	}
		
	public void update(){
		Data data = Data.getInstance();
		
		this.today = data.getToday();
		this.upcoming = data.getUpcoming();
		this.someday = data.getSomeday();
	}
	
	protected String getToday() {
		i=0;
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
		
		for (Task task : taskArray) {
			i++;
			tasks += "  "+i + "." + task.toString() + "\n";
		}
		return tasks;
	}
	
	public void show() throws BadLocationException {
		// TODO Auto-generated method stub
		UserInterface UI = UserInterface.getInstance();
		JTextPane showToUser = UI.getShowToUser();
		
		StyledDocument doc = showToUser.getStyledDocument();
		Style style = showToUser.addStyle("Style", null);
		
		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "  Today: \n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), getToday(), style);

		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "\n  Upcoming: \n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), getUpcoming(), style);

		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "\n  Someday: \n", style);
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
			int upcomingIndex = index - todaySize;
			return upcoming.get(upcomingIndex);
		}
		else if(index < allSize){
			int somedayIndex = index - dateSize;
			return someday.get(somedayIndex);
		}
		else{
			return new Task();
		}
	}
	
}
