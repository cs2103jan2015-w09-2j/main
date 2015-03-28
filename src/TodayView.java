import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class TodayView extends SingleView implements View{
	
	private String taskDes;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private boolean isOverdue;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
	
	@Override
	public void update() {	
		setList(data.getToday());	
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
		int i = 0;
		 String tasks = "";
			
		 for (Task task : getList()) {
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

	@Override
	public void show() throws BadLocationException {
		// TODO Auto-generated method stub
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
		
	}

}
