//@author A0112715

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
	private String taskDes;
	private String todayDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private boolean isOverdue;
	private DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("EEE, dd MMMM");
	private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern(
			"h:mm a", Locale.US);
	private StringBuilder output = new StringBuilder();
	
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
			if (i < 12) {
				i++;
				getTaskInfo(task);
				String t = "";
				String taskNo = "" + i + ".   ";
				if (task.isDeadlineTask()) {
					String tasks = taskDes;
					t = endTime.format(formatTime).replace("AM", "am")
							.replace("PM", "pm").replace(".00", "");
					t = t.toString().replaceAll("\\[", "")
							.replaceAll("\\]", " -");
					if (task.getIsCompleted()) { //coloured green and striked thru
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FFFFFF", "!", 2);
						appendTasks("#00B800", "<strike>"+t+"</strike>", 3);
						appendTasks("#00B800", "<strike>"+tasks+"</strike>", 4);
					} else if (task.isOverdue()) {
						
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FF0000", "!", 2);
						appendTasks("#01A9DB", t, 3);
						appendTasks("#4B088A", tasks, 4);
					} else {
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FFFFFF", "!", 2);
						appendTasks("#01A9DB", t, 3);
						appendTasks("#4B088A", tasks, 4);
					}

				} else {
					String tasks = taskDes;
					t = startTime.format(formatTime).replace("AM", "am")
							.replace("PM", "pm").replace(".00", "")
							+ " - "
							+ endTime.format(formatTime).replace("AM", "am")
									.replace("PM", "pm").replace(".00", "");
					t = t.toString().replaceAll("\\[", "")
							.replaceAll("\\]", " -");

					if (task.getIsCompleted()) { //coloured green and striked thru
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FFFFFF", "!", 2);
						appendTasks("#00B800", "<strike>"+t+"</strike>", 3);
						appendTasks("#00B800", "<strike>"+tasks+"</strike>", 4);

					} else if (task.isOverdue()) {
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FF0000", "!", 2);
						appendTasks("#01A9DB", t, 3);
						appendTasks("#4B088A", tasks, 4);
					}  else {
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FFFFFF", "!", 2);
						appendTasks("#01A9DB", t, 3);
						appendTasks("#4B088A", tasks, 4);
					}

				}
			}
		}
	}

	public void appendTasks(String textColour, String s, int row)
			throws BadLocationException {
		if (row == 1) {
			output.append("<tr width=\"100px\" >"
					+ "<td width=\"40px\"><font size=\"4\" color=\""
					+ textColour + "\"><p align=\"right\"><b>" + s
					+ "</b></p></font></td>");
		} else if (row == 2) {
			// output.append("<td width=\"1px\"><img src=\"alert.jpg\"></td>");
			output.append("<td width=\"1px\"><font size=\"5\" color=\""
					+ textColour + "\"><p align=\"center\"><b>" + s
					+ "</b></p></font></td>");
		} else if (row == 3) {
			output.append("<td width=\"140px\"><font size=\"4\" color=\""
					+ textColour + "\"><p align=\"left\"><b>" + s + "</b></p></font></td>");
		} else if (row == 4) {
			output.append("<td width=\"420px\"><font size=\"5\" color=\""
					+ textColour + "\"><p align=\"left\">" + s + "</p></font></td></tr>");
		}

	}

	@Override
	public String show() throws BadLocationException {
		// TODO Auto-generated method stub
		output = new StringBuilder();
		getTodayDate();					                                    	
		output.append("<html>");
		output.append("&nbsp");
		output.append("<table cellspacing=\"2px\" cellpadding=\"2px\" width=\"100%\">");
		output.append("<tr width=\"100px\" bgcolor=\"#084B8A\"><td  height =\"30px\" width=\"100px\"colspan=\"4\"><font size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Today ("+todayDate+") </b></p></font></td></tr>");
		getToday();
		output.append("&nbsp");
		output.append("</table>");
		output.append("</table></html>");
		return output.toString();

	}

}
