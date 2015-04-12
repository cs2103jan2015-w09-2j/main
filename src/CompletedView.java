//@author A0112715R
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.text.BadLocationException;

public class CompletedView extends SingleView implements View {
	private StringBuilder output = new StringBuilder();
	private DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("dd-MM-yyyy");
	private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h.mma",
			Locale.US);
	private String taskDes;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private int i = 0;
	private int taskLimit = 10;

	@Override
	public void update() {
		Data data = Data.getInstance();

		setList(data.getCompleted());
	}

	/**
	 * Gets the information of each task
	 * 
	 * @param task
	 */
	private void getTaskInfo(Task task) {
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

	/**
	 * Gets all completed tasks and sends for formatting according to task type
	 * 
	 */
	private void getCompletedTasks() throws BadLocationException {

		for (Task task : getList()) {
			if (i < taskLimit) {
				i++;
				getTaskInfo(task);
				String taskNo = "     " + i + ".   ";
				if (task.isDeadlineTask()) {
					formatDeadlineTask(taskNo);
				} else {
					formatTimedAndFloatingTasks(task, taskNo);
				}
			}
		}
	}

	/**
	 * Formats the deadline tasks
	 * 
	 * @param task
	 *            ,taskNo
	 */
	private void formatDeadlineTask(String taskNo) throws BadLocationException {
		String tasks = taskDes;
		String endDateTime = formatEndDateTime();
		formatTasks(taskNo, endDateTime, tasks);
	}

	/**
	 * Formats the timed and floating tasks
	 * 
	 * @param task
	 *            ,taskNo
	 */
	private void formatTimedAndFloatingTasks(Task task, String taskNo)
			throws BadLocationException {
		String tasks = taskDes;
		String dateToDisplay = "---";
		String timeToDisplay = "---";

		if (!task.isFloatingTask()) {
			dateToDisplay = formatStartEndDate();
			timeToDisplay = formatStartEndTime();
		}
		String dateTime = dateToDisplay + timeToDisplay;
		formatTasks(taskNo, dateTime, tasks);

	}

	private void formatTasks(String taskNo, String dateTime, String tasks)
			throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FFFFFF", "!", 2);
		appendTasks("#01A9DB", dateTime, 3);
		appendTasks("#0A1B2A", tasks, 4);
	}

	private String formatEndDateTime() {
		String endDateToDisplay = endDate.format(formatter);
		String endTimeToDisplay = endTime.format(formatTime);
		endDateToDisplay = endDateToDisplay.replaceAll("\\[", "").replaceAll(
				"\\]", "");
		endTimeToDisplay = endTimeToDisplay.replaceAll("\\[", "").replaceAll(
				"\\]", "");
		endTimeToDisplay = endTimeToDisplay.replace("AM", "am")
				.replace("PM", "pm").replace(".00", "");

		String endDateTime = "<p align=\"left\">" + endDateToDisplay + "</p>"
				+ "<p align=\"left\"><font color=\"#1F3D7A\">"
				+ endTimeToDisplay + "</font></p>";
		return endDateTime;
	}

	private String formatStartEndDate() {
		String startDateToDisplay = startDate.format(formatter);
		startDateToDisplay = startDateToDisplay.replaceAll("\\[", "")
				.replaceAll("\\]", "");

		String endDateToDisplay = endDate.format(formatter);
		endDateToDisplay = endDateToDisplay.replaceAll("\\[", "").replaceAll(
				"\\]", "");

		String dateToDisplay = "<p align=\"left\">" + startDateToDisplay
				+ " - " + endDateToDisplay + "</p>";
		;
		return dateToDisplay;

	}

	private String formatStartEndTime() {
		String startTimeToDisplay = startTime.format(formatTime);
		startTimeToDisplay = startTimeToDisplay.replaceAll("\\[", "")
				.replaceAll("\\]", "");
		startTimeToDisplay = startTimeToDisplay.replace("AM", "am")
				.replace("PM", "pm").replace(".00", "");

		String endTimeToDisplay = endTime.format(formatTime);
		endTimeToDisplay = endTimeToDisplay.replaceAll("\\[", "").replaceAll(
				"\\]", "");
		endTimeToDisplay = endTimeToDisplay.replace("AM", "am")
				.replace("PM", "pm").replace(".00", "");
		String timeToDisplay = "<p align=\"left\"><font color=\"#1F3D7A\">"
				+ startTimeToDisplay + " - " + endTimeToDisplay + "</font></p>";

		return timeToDisplay;
	}

	/**
	 * Appends to Stringbuilder output
	 * 
	 * @param textColour
	 *            ,text,row
	 */
	public void appendTasks(String textColour, String text, int row)
			throws BadLocationException {
		if (row == 1) {
			output.append("<tr width=\"100px\" >" + "<td valign=\"top\""
					+ " width=\"40px\"><font size=\"4\" color=\"" + textColour
					+ "\"><p align=\"right\"><b>" + text + "</b></p></font></td>");
		} else if (row == 2) {
			// output.append("<td width=\"1px\"><img src=\"alert.jpg\"></td>");
			output.append("<td valign=\"top\" width=\"1px\"><font size=\"4.5\" color=\""
					+ textColour
					+ "\"><p align=\"center\"><b>"
					+ text
					+ "</b></p></font></td>");
		} else if (row == 3) {
			output.append("<td valign=\"top\" width=\"180px\"><font face=\"Rockwell\" size=\"3.5\" color=\""
					+ textColour + "\"><b>" + text + "</b></font></td>");
		} else if (row == 4) {
			output.append("<td valign=\"top\" width=\"420px\"><font face=\"Eras Demi ITC\" size=\"3.5\" color=\""
					+ textColour
					+ "\"><p align=\"left\">"
					+ text
					+ "</p></font></td></tr>");
		} else if (row == 5) {
			output.append("<td valign=\"top\" colspan=\"420px\" width=\"420px\"><font face=\"Eras Demi ITC\" size=\"3.5\" color=\""
					+ textColour
					+ "\"><p align=\"left\">"
					+ text
					+ "</p></font></td></tr>");
		}

	}

	/**
	 * Table created using Html code to append to JTextpane
	 * 
	 * @return String
	 */
	@Override
	public String show() throws BadLocationException {
		output = new StringBuilder();
		// Display display = Display.getInstance();
		// page = display.getPaging();
		output.append("<html>");
		output.append("<table STYLE=\"margin-bottom: 15px;\" cellpadding=\"7px\" cellspacing=\"0px\" width=\"100%\">");
		output.append("<tr STYLE=\"margin-bottom: 5px;\" width=\"100px\" bgcolor=\"#084B8A\"><td height =\"30px\" width=\"100px\"colspan=\"4\"><font face=\"Tempus Sans ITC\" size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Completed</b></p></font></td></tr>");
		getCompletedTasks();
		output.append("</table>");

		output.append("</html>");

		return output.toString();

	}

	/**
	 * This is used for Testing
	 * 
	 * @return ArrayList<Task>
	 */
	public ArrayList<Task> getCompletedList() {
		ArrayList<Task> completedList = data.getCompleted();
		return completedList;
	}

}
