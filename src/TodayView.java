//@author A0112715R

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class TodayView  extends SingleView implements View{
	private String taskDes;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
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

	//
	// protected void isTaskOverdue(Task task) {
	// isOverdue = false;
	// LocalDateTime now = LocalDateTime.now();
	// LocalDateTime endDateTime = task.getEnd();
	//
	// if (endDateTime.isBefore(now)) {
	// isOverdue = true;
	// }
	// }
	
	protected ArrayList<Task> returnTodayList(){
		return  getList();
	}

	protected void getToday() throws BadLocationException {
		int i = 0;
		for (Task task : getList()) {
			//if(pageNo =1){
			if (i < 15) {
				i++;
				getTaskInfo(task);
				if (task.isDeadlineTask()) {
					formatDeadlineTask(task, i);

				} else {
					String taskNo = "" + i + ".   ";
					String tasks = taskDes;
					String timeToDisplay = formatTimeToDisplay();

					if (task.getIsCompleted()) {
						formatCompletedTasks(taskNo, timeToDisplay, tasks);

					} else if (task.isOverdue()) {
						formatOverdueTasks(taskNo, timeToDisplay, tasks);
					} else {
						formatTasks(taskNo, timeToDisplay, tasks);
					}

				}
			}
		}
		//}
	}


	private String formatTimeToDisplay() {
		String timeToDisplay = startTime.format(formatTime).replace("AM", "am")
				.replace("PM", "pm").replace(".00", "")
				+ " - "
				+ endTime.format(formatTime).replace("AM", "am")
						.replace("PM", "pm").replace(".00", "");

		timeToDisplay = timeToDisplay.toString().replaceAll("\\[", "")
				.replaceAll("\\]", " -");

		return timeToDisplay;

	}

	private void formatDeadlineTask(Task task, int i)
			throws BadLocationException {
		String tasks = taskDes;
		String endTimeToDisplay = "";
		String taskNo = "" + i + ".   ";
		endTimeToDisplay = endTime.format(formatTime).replace("AM", "am")
				.replace("PM", "pm").replace(".00", "");
		endTimeToDisplay = endTimeToDisplay.toString().replaceAll("\\[", "")
				.replaceAll("\\]", " -");
		if (task.getIsCompleted()) {
			formatCompletedTasks(taskNo, endTimeToDisplay, tasks);
		} else if (task.isOverdue()) {
			formatOverdueTasks(taskNo, endTimeToDisplay, tasks);
		} else {
			formatTasks(taskNo, endTimeToDisplay, tasks);
		}

	}

	private void formatTasks(String taskNo, String timeToDisplay, String tasks)
			throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FFFFFF", "!", 2);
		appendTasks("#01A9DB", timeToDisplay, 3);
		appendTasks("#0A1B2A", tasks, 4);
	}

	private void formatCompletedTasks(String taskNo, String timeToDisplay,
			String tasks) throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FFFFFF", "!", 2);
		appendTasks("#848484", "<strike>"+timeToDisplay+"</strike>", 3);
		appendTasks("#848484", "<strike>"+tasks+"</strike>", 4);


	}

	private void formatOverdueTasks(String taskNo, String timeToDisplay,
			String tasks) throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FF0000", "!", 2);
		appendTasks("#01A9DB", timeToDisplay, 3);
		appendTasks("#0A1B2A", tasks, 4);
	}

	public void appendTasks(String textColour, String s, int row)
			throws BadLocationException {
		if (row == 1) {
			output.append("<tr width=\"100px\" >"
					+ "<td valign=\"top\""
					+ " width=\"40px\"><font size=\"4\" color=\""
					+ textColour + "\"><p align=\"right\"><b>" + s
					+ "</b></p></font></td>");
		} else if (row == 2) {
			// output.append("<td width=\"1px\"><img src=\"alert.jpg\"></td>");
			output.append("<td valign=\"top\" width=\"1px\"><font size=\"4.5\" color=\""
					+ textColour + "\"><p align=\"center\"><b>" + s
					+ "</b></p></font></td>");
		} else if (row == 3) {
			output.append("<td valign=\"top\" width=\"180px\"><font face=\"Rockwell\" size=\"3.5\" color=\""
					+ textColour + "\"><p align=\"left\"><b>" + s + "</b></p></font></td>");
		} else if (row == 4) {
			output.append("<td valign=\"top\" width=\"420px\"><font face=\"Eras Demi ITC\" size=\"3.5\" color=\""
					+ textColour + "\"><p align=\"left\">" + s + "</p></font></td></tr>");
		}

	}

	@Override
	public String show() throws BadLocationException {
		// TODO Auto-generated method stub
		output = new StringBuilder();

		output.append("<html>");
		output.append("<table  STYLE=\"margin-bottom: 15px;\" cellpadding=\"3px\" cellspacing=\"0px\" width=\"100%\">");
		output.append("<tr STYLE=\"margin-bottom: 5px;\" width=\"100px\" bgcolor=\"#084B8A\"><td  height =\"30px\" width=\"100px\"colspan=\"4\"><font face=\"Tempus Sans ITC\" size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Today</b></p></font></td></tr>");
		getToday();
		output.append("</table>");
		output.append("</table></html>");
		return output.toString();

	}

}
