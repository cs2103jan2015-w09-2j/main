//@author A0112715
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

public class DateView implements View {
	private UserInterface UI;
	private JTextPane showToUser;
	private StyledDocument doc;
	private Style style;
	private ArrayList<Task> today;
	private ArrayList<Task> upcoming;
	private ArrayList<Task> someday;
	private int i = 0;
	private int noOfOverdueTasks = 0;
	private String taskDes;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private boolean isOverdue;
	private DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("dd-MM-yyyy");
	private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern(
			"h:mm a", Locale.US);

	public DateView() {
		update();
	}

	public void update() {
		Data data = Data.getInstance();

		this.today = data.getToday();
		this.upcoming = data.getUpcoming();
		this.someday = data.getSomeday();
	}

	protected void getTaskInfo(Task task) {
		taskDes = task.getDescription();
		if (!task.isFloatingTask()) {
			if (!task.isDeadlineTask()) {
				startDate = task.getStart().toLocalDate();
				startTime = task.getStart().toLocalTime();
			}
			endDate = task.getEnd().toLocalDate();
			endTime = task.getEnd().toLocalTime();
		}
	}

	protected void getToday() throws BadLocationException {
		i = 0;
		for (Task task : today) {
			i++;
			getTaskInfo(task);
			isTaskOverdue(task);
			String t = "";
			String numbering = "     " + i + ".   ";
			if (task.isDeadlineTask()) {
				String tasks = taskDes;
				t = "  (by "
						+ endTime.format(formatTime).replace("AM", "am")
								.replace("PM", "pm") + ")";
				t = t.toString().replaceAll("\\[", "").replaceAll("\\]", " -");
				if (isOverdue) {
					
					appendTasks(Color.GRAY, Color.WHITE, false, numbering);
					appendTasks(Color.RED, Color.WHITE, true, "! ");
					appendTasks(Color.BLUE.darker(), Color.WHITE, false,
							tasks);
					appendTasks(Color.BLUE.darker(), Color.WHITE, false, t
							+ "\n");
				} else {
					appendTasks(Color.GRAY, Color.white, false, numbering);
					appendTasks(Color.BLUE.darker(), Color.white, false, tasks);
					appendTasks(Color.CYAN.darker(), Color.white, false, t
							+ "\n");
				}

			} else {
				String tasks = taskDes;
				t = startTime.format(formatTime).replace("AM", "am")
						.replace("PM", "pm")
						+ " to "
						+ endTime.format(formatTime).replace("AM", "am")
								.replace("PM", "pm") + ": ";
				t = t.toString().replaceAll("\\[", "").replaceAll("\\]", " -");

				if (isOverdue) {
					appendTasks(Color.GRAY, Color.white, false, numbering);
					appendTasks(Color.RED, Color.WHITE, true, "! ");
					appendTasks(Color.CYAN.darker(), Color.WHITE, false, t);
					appendTasks(Color.BLUE.darker(), Color.WHITE, false, tasks
							+ "\n");
				} else {
					appendTasks(Color.GRAY, Color.white, false, numbering);
					appendTasks(Color.CYAN.darker(), Color.white, false, t);
					appendTasks(Color.BLUE.darker(), Color.white, false, tasks
							+ "\n");
				}

			}

		}
	}

	protected void getUpcoming() throws BadLocationException {

		for (Task task : upcoming) {
			i++;
			getTaskInfo(task);
			isTaskOverdue(task);
			String t = "";
			String numbering = "     " + i + ".   ";
			if (task.isDeadlineTask()) {
				String tasks = taskDes;
				t = " (by " + endDate.format(formatter) + ")";
				t = t.replaceAll("\\[", "").replaceAll("\\]", " -");
				if (isOverdue) {
					appendTasks(Color.GRAY, Color.WHITE, false, numbering);
					appendTasks(Color.RED, Color.WHITE, true, "! ");
					appendTasks(Color.BLUE.darker(), Color.WHITE, false,
							tasks);
					appendTasks(Color.BLUE.darker(), Color.WHITE, false, t
							+ "\n");
				} else {
					appendTasks(Color.GRAY, Color.white, false, numbering);
					appendTasks(Color.BLUE.darker(), Color.white, false, tasks);
					appendTasks(Color.CYAN.darker(), Color.white, false, t
							+ "\n");
				}
			}

			else {
				String tasks = taskDes;

				t = "  (starts on " + startDate.format(formatter) + ")";
				t = t.replaceAll("\\[", "").replaceAll("\\]", " -");
				if (isOverdue) {
					appendTasks(Color.GRAY, Color.WHITE, false, numbering);
					appendTasks(Color.RED, Color.WHITE, true, "! ");
					appendTasks(Color.BLUE.darker(), Color.WHITE, false,
							tasks);
					appendTasks(Color.BLUE.darker(), Color.WHITE, false, t
							+ "\n");
				} else {
					appendTasks(Color.GRAY, Color.white, false, numbering);
					appendTasks(Color.BLUE.darker(), Color.white, false, tasks);
					appendTasks(Color.CYAN.darker(), Color.white, false, t
							+ "\n");
				}
			}
		}
	}

	protected void getSomeday() throws BadLocationException {

		for (Task task : someday) {
			String tasks = "";
			i++;
			String numbering = "     " + i + ".   ";
			appendTasks(Color.GRAY.brighter(), Color.WHITE, false, numbering);
			String t = task.toString().replaceAll("-", "to");
			t = task.toString().replaceAll("\\[", "").replaceAll("\\]", " -");
			tasks = t + "\n";
			appendTasks(Color.BLUE.darker(), Color.WHITE,false, tasks);
		}

	}

	protected void isTaskOverdue(Task task) {
		isOverdue = false;
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime endDateTime = task.getEnd();

		if (endDateTime.isBefore(now)) {
			isOverdue = true;
		}
	}

	public void appendTasks(Color c, Color bg, boolean isBold, String s)
			throws BadLocationException {
		StyleConstants.setBold(style, isBold);
		StyleConstants.setFontSize(style, 14);
		StyleConstants.setBackground(style, bg);
		StyleConstants.setForeground(style, c);
		doc.insertString(doc.getLength(), s, style);
		
	}

	public void show() throws BadLocationException {
		UI = UserInterface.getInstance();
		showToUser = UI.getShowToUser();
		doc = showToUser.getStyledDocument();
		style = showToUser.addStyle("Style", null);
//		StyleConstants.setBold(style, true);
//		StyleConstants.setFontSize(style, 4);
//		StyleConstants.setBackground(style, new Color(84, 121, 163));
//		doc.insertString(doc.getLength(),
//				"\n\n\n							                                        ", style);
		StyleConstants.setFontSize(style, 15);
		StyleConstants.setForeground(style, Color.WHITE);
		
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		doc.insertString(doc.getLength(),
					"\n			   Today                                            	        \n", style); 
		doc.insertString(doc.getLength(),"\n", style); 
//
//		StyleConstants.setFontSize(style, 4);
//		StyleConstants.setBackground(style, new Color(84, 121, 163));
//		doc.insertString(doc.getLength(),
//				"							                                        \n\n\n", style);
//		StyleConstants.setForeground(style, Color.BLACK);
		StyleConstants.setBackground(style, Color.WHITE);
		getToday();

		StyleConstants.setBold(style, true);
//		StyleConstants.setFontSize(style, 4);
//		StyleConstants.setBackground(style, new Color(84, 121, 163));
//		doc.insertString(doc.getLength(),
//				"\n\n\n							                                        ", style);
		StyleConstants.setFontSize(style, 15);
		StyleConstants.setForeground(style, Color.WHITE);
		doc.insertString(doc.getLength(),"\n", style); 
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		doc.insertString(doc.getLength(),
					"			   Upcoming                                 	        \n", style); 
//		StyleConstants.setFontSize(style, 4);
//		StyleConstants.setBackground(style, new Color(84, 121, 163));
//		doc.insertString(doc.getLength(),
//				"							                                        \n\n\n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		StyleConstants.setBackground(style, Color.WHITE);
		doc.insertString(doc.getLength(),"\n", style); 
		getUpcoming();

//		StyleConstants.setBold(style, true);
//		StyleConstants.setFontSize(style, 4);
//		StyleConstants.setBackground(style, new Color(84, 121, 163));
//		doc.insertString(doc.getLength(),
//				"\n\n\n							                                        ", style);
		StyleConstants.setFontSize(style, 15);
		StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.WHITE);
		doc.insertString(doc.getLength(),"\n", style); 
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		doc.insertString(doc.getLength(),
					"			   Someday                                 	        \n", style); 
//		StyleConstants.setFontSize(style, 4);
//		StyleConstants.setBackground(style, new Color(84, 121, 163));
//		doc.insertString(doc.getLength(),
//				"							                                        \n\n\n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		StyleConstants.setBackground(style, Color.WHITE);
		doc.insertString(doc.getLength(),"\n", style); 
		getSomeday();
		StyleConstants.setForeground(style, Color.BLUE.brighter());

	}

	public Task getTask(int numbering) throws IndexOutOfBoundsException {
		int index = numbering - 1;

		int todaySize = today.size();
		int dateSize = todaySize + upcoming.size();
		int allSize = dateSize + someday.size();

		if (index > -1 && index < todaySize) {
			return today.get(index);
		} else if (index < dateSize) {
			int upcomingIndex = index - todaySize;
			return upcoming.get(upcomingIndex);
		} else { // index < allSize
			int somedayIndex = index - dateSize;
			return someday.get(somedayIndex);
		}
	}

}
