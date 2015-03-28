import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

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
	private String taskDes;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private boolean isOverdue;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
	
	public DateView(){
		update();
	}
		
	public void update(){
		Data data = Data.getInstance();
		
		this.today = data.getToday();
		this.upcoming = data.getUpcoming();
		this.someday = data.getSomeday();
	}
	
	protected void getTaskInfo(Task task) {
		taskDes = task.getDescription();
		if(!task.isFloatingTask()){
		if(!task.isDeadlineTask()){
		startDate = task.getStart().toLocalDate();
		startTime = task.getStart().toLocalTime();
		}
		endDate = task.getEnd().toLocalDate();
		endTime = task.getEnd().toLocalTime();
		}
	}

	protected String getToday() {
		i = 0;
		 String tasks = "";
			
		 for (Task task : today) {
		 i++;
		 getTaskInfo(task);
		 String t="";
		 if(task.isDeadlineTask()){
			 t = taskDes +" (by " + endTime.format(formatTime).replace("AM", "am").replace("PM", "pm")+")";
		 }
		 else{
		 t = startTime.format(formatTime).replace("AM", "am").replace("PM", "pm")+" to "+endTime.format(formatTime).replace("AM", "am").replace("PM", "pm") +": " + taskDes;
		 }
		 t = t.toString().replaceAll("\\[", "").replaceAll("\\]"," -");
		 tasks += "  "+i + ".  " + t + "\n";
		 }
		 return tasks;
	}

	protected String getUpcoming() {
		 String tasks = "";
		
		 for (Task task : upcoming) {
		 i++;
		 getTaskInfo(task);
		 String t = "";
		 if(task.isDeadlineTask()){
		 t = taskDes +" (by " + endDate.format(formatter)+")";
		 }

		 else{
		 t = taskDes +"(starts on " +startDate.format(formatter)+")";
		 t = t.replaceAll("\\[", "").replaceAll("\\]"," -");
		 }
		 tasks += "  "+i + ".  " + t + "\n";
		 }
		 return tasks;
	}

	protected String getSomeday() {
		 String tasks = "";
			
		 for (Task task : someday) {
		 i++;
		 String t = task.toString().replaceAll("-", "to");
		 t = task.toString().replaceAll("\\[", "").replaceAll("\\]"," -");
		 tasks += "  "+i + ".  " + t + "\n";
		 }
		 return tasks;

	}

	protected boolean isTaskOverdue(Task task) {
		isOverdue = false;
		LocalDate now = LocalDate.now();

		if (endDate.isBefore(now)) {
			isOverdue = true;
		}
		return isOverdue;
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
		doc.insertString(doc.getLength(), "\n"+getToday()+"\n", style);
		

		StyleConstants.setForeground(style, Color.WHITE);
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		doc.insertString(doc.getLength(), "  			   Upcoming    			        \n", style);	
		
		StyleConstants.setForeground(style, Color.BLACK);
		StyleConstants.setBackground(style, Color.WHITE);
		doc.insertString(doc.getLength(), "\n"+getUpcoming()+"\n", style);		

		StyleConstants.setForeground(style, Color.WHITE);
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		doc.insertString(doc.getLength(), "  			   Someday   			        \n", style);
		
			
		StyleConstants.setForeground(style, Color.BLACK);
		StyleConstants.setBackground(style, Color.WHITE);
		doc.insertString(doc.getLength(), "\n"+getSomeday()+"\n", style);
		StyleConstants.setForeground(style, Color.BLUE.brighter());
			
	}
	
	public Task getTask(int numbering){
		int index = numbering - 1;
		
		int todaySize = today.size();
		int dateSize = todaySize + upcoming.size();
		int allSize = dateSize + someday.size();
		
		if(index < 0 || index >= allSize){
			return null;
		}
		if(index > -1 && index < todaySize){
			return today.get(index);
		}
		else if(index < dateSize){
			int upcomingIndex = index - todaySize;
			return upcoming.get(upcomingIndex);
		}
		else{ //index < allSize
			int somedayIndex = index - dateSize;
			return someday.get(somedayIndex);
		}
	}
	
}
