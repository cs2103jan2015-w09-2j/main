import java.awt.Color;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class DateView implements View{

	private ArrayList<Task> today;
	private ArrayList<Task> upcoming;
	private ArrayList<Task> someday;
	private int i = 0;
	private Task task = new Task();
	
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
			//String t = task.toString().replaceAll("-", "to");
			String t =	task.toString().replaceAll("\\[", "").replaceAll("\\]"," -");
			tasks += "  		   "+i + ".  " + t + "\n";
		}
		return tasks;
	}
	
	public LocalDateTime getStartDate(){
		LocalDateTime startDate = task.getStart();
		return startDate;
		
	}
	
	public LocalDateTime getEndDate(){
		LocalDateTime endDate = task.getEnd();
		return endDate;
		
	}
	
	public String getTask(){
		String taskDesc = task.getDescription();
		return taskDesc;
	}
	
	public void show() throws BadLocationException {
		UserInterface UI = UserInterface.getInstance();
		JTextPane showToUser = UI.getShowToUser();
		
		StyledDocument doc = showToUser.getStyledDocument();
		Style style = showToUser.addStyle("Style", null);
		
		StyleConstants.setForeground(style, Color.WHITE);
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		doc.insertString(doc.getLength(), "\n  			   Today  			        \n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		StyleConstants.setBackground(style, Color.WHITE);
		doc.insertString(doc.getLength(), getToday()+"\n", style);
		

		StyleConstants.setForeground(style, Color.WHITE);
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		doc.insertString(doc.getLength(), "  			   Upcoming    			        \n", style);	
		
		StyleConstants.setForeground(style, Color.BLACK);
		StyleConstants.setBackground(style, Color.WHITE);
		doc.insertString(doc.getLength(), getUpcoming()+"\n", style);		

		StyleConstants.setForeground(style, Color.WHITE);
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		doc.insertString(doc.getLength(), "  			   Someday   			        \n", style);
		
			
		StyleConstants.setForeground(style, Color.BLACK);
		StyleConstants.setBackground(style, Color.WHITE);
		doc.insertString(doc.getLength(), getSomeday()+"\n", style);
		StyleConstants.setForeground(style, Color.BLUE.brighter());
			
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
