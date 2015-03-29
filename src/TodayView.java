import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class TodayView extends SingleView implements View {
	private UserInterface UI = UserInterface.getInstance();
	private JTextPane showToUser = UI.getShowToUser();
	private StyledDocument doc = showToUser.getStyledDocument();
	private Style style = showToUser.addStyle("Style", null);
	private String taskDes;
	private String todayDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private boolean isOverdue;
	private DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("dd-MM-yyyy");
	private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern(
			"h:mm a", Locale.US);

	@Override
	public void update() {
		setList(data.getToday());
	}

	protected void getTaskInfo(Task task) {
		taskDes = task.getDescription();
		if (!task.isFloatingTask()) {
			if (!task.isDeadlineTask()) {
				startTime = task.getStart().toLocalTime();
			}
			endDate = task.getEnd().toLocalDate();
			endTime = task.getEnd().toLocalTime();
		}
	}

	
	protected void getTodayDate() {
		todayDate = LocalDate.now().format(formatter);
	}

	protected void isTaskOverdue(Task task) {
		isOverdue = false;
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime endDateTime = task.getEnd();
		
		if (endDateTime.isBefore(now)) {
			isOverdue = true;
		}
	}
	
	protected void getToday() throws BadLocationException {
		int i = 0;
		for (Task task : getList()) {
			i++;
			getTaskInfo(task);
			isTaskOverdue(task);
			String t = "";
			String numbering = "  "+i+".  ";
			if (task.isDeadlineTask()) {
				String tasks = taskDes;
				t =  " (by "
						+ endTime.format(formatTime).replace("AM", "am")
								.replace("PM", "pm") + ")";
				t = t.toString().replaceAll("\\[", "").replaceAll("\\]", " -");
				if(isOverdue){
					appendTasks(Color.GRAY,Color.WHITE, numbering);
					appendTasks(Color.MAGENTA.darker(),Color.WHITE, tasks);
					appendTasks(Color.MAGENTA.darker(), Color.WHITE, t+"\n");
				}
				else{
				appendTasks(Color.GRAY,Color.white, numbering);
				appendTasks(Color.BLUE.darker(),Color.white, tasks);
				appendTasks(Color.CYAN.darker(),Color.white, t+"\n");
				}
				
			} else {
				String tasks = taskDes;
				t = startTime.format(formatTime).replace("AM", "am")
						.replace("PM", "pm")
						+ " to "
						+ endTime.format(formatTime).replace("AM", "am")
								.replace("PM", "pm") + ": ";
				t = t.toString().replaceAll("\\[", "").replaceAll("\\]", " -");
				
				if(isOverdue){
					appendTasks(Color.GRAY, Color.white, numbering);
					appendTasks(Color.CYAN.darker(), Color.WHITE, t);
					appendTasks(Color.BLUE.darker(), Color.WHITE, tasks+"\n");
				}
				else{
					appendTasks(Color.GRAY, Color.white, numbering);
					appendTasks(Color.CYAN.darker(), Color.white, t);
					appendTasks(Color.BLUE.darker(), Color.white, tasks+"\n");
				}

			}

		}
	}

	public void appendTasks(Color c, Color bg, String s) throws BadLocationException {
		StyleConstants.setBold(style, false);
		StyleConstants.setFontSize(style, 16);
		StyleConstants.setBackground(style,bg);
		StyleConstants.setForeground(style, c);
		doc.insertString(doc.getLength(), s, style);
	}

	@Override
	public void show() throws BadLocationException {
		// TODO Auto-generated method stub
		getTodayDate();
		StyleConstants.setForeground(style, Color.WHITE);
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		StyleConstants.setBold(style, true);
		StyleConstants.setFontSize(style, 18);
		doc.insertString(doc.getLength(), "\n  		     Today (" + todayDate
				+ ")	                   \n", style);
		doc.insertString(doc.getLength(),"\n",style);
		getToday();

	}

}
