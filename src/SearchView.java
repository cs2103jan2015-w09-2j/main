//@author A0112715R
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.text.BadLocationException;

public class SearchView extends SingleView implements View {
	private String searchedText = "";
	private String taskDes;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("dd-MM-yyyy");
	private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h.mma",
			Locale.US);
	private StringBuilder output;
	private int taskLimit = 10;

	/**
	 * Gets the task containing the keyword specified
	 * 
	 * @param searchedText
	 */
	public SearchView(String searchedText) {
		System.out.println("search:" + searchedText);
		this.searchedText = searchedText;
		update();
	}

	@Override
	public void update() {
		setList(data.getSearched(searchedText));
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
	 * Gets the search results
	 */
	private void getSearchResults() throws BadLocationException {
		int i = 0;
		for (Task task : getList()) {
			if (i < taskLimit) {
				i++;
				getTaskInfo(task);
				String taskNo = "     " + i + ".   ";
				if (task.isDeadlineTask()) {
					formatDeadlineTasks(task, taskNo);
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
	private void formatDeadlineTasks(Task task, String taskNo)
			throws BadLocationException {
		String tasks = taskDes;

		String endDateTime = formatEndDateTime(task);

		if (task.getIsCompleted()) {
			formatCompletedTasks(taskNo, endDateTime, tasks);

		} else if (task.isOverdue()) {
			fomatOverdueTasks(taskNo, endDateTime, tasks);

		} else {
			formatTasks(taskNo, endDateTime, tasks);
		}

	}

	private void formatCompletedTasks(String taskNo, String endDateTime,
			String tasks) throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FFFFFF", "!", 2);
		appendTasks("#00A300", endDateTime, 3);
		appendTasks("#00A300", tasks, 4);

	}

	private String formatEndDateTime(Task task) {
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

		if (task.getIsCompleted()) {
			endDateTime = "<p align=\"left\">" + endDateToDisplay + "</p>"
					+ "<p align=\"left\"><font color=\"#00A300\">"
					+ endTimeToDisplay + "</font></p>";
		}

		return endDateTime;
	}

	private void formatTasks(String taskNo, String dateTime, String tasks)
			throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FFFFFF", "!", 2);
		appendTasks("#01A9DB", dateTime, 3);
		appendTasks("#0A1B2A", tasks, 4);
	}

	private void fomatOverdueTasks(String taskNo, String endDateTime,
			String tasks) throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FF0000", "!", 2);
		appendTasks("#01A9DB", endDateTime, 3);
		appendTasks("#0A1B2A", tasks, 4);

	}

	/**
	 * Formats the timed tasks and floating tasks
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
		if (task.getIsCompleted()) {
			formatCompletedTasks(taskNo, dateTime, tasks);

		} else if (task.isOverdue()) {
			fomatOverdueTasks(taskNo, dateTime, tasks);
		} else {
			formatTasks(taskNo, dateTime, tasks);
		}

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
					+ "\"><p align=\"right\"><b>" + text
					+ "</b></p></font></td>");
		} else if (row == 2) {
			// output.append("<td width=\"1px\"><img src=\"alert.jpg\"></td>");
			output.append("<td valign=\"top\" width=\"1px\"><font size=\"4.5\" color=\""
					+ textColour
					+ "\"><p align=\"center\"><b>"
					+ text
					+ "</b></p></font></td>");
		} else if (row == 3) {
			output.append("<td valign=\"top\" width=\"200px\"><font face=\"Rockwell\" size=\"3.5\" color=\""
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
		output.append("<html>");
		output.append("<table  STYLE=\"margin-bottom: 15px;\" cellpadding=\"3px\" cellspacing=\"0px\" width=\"100%\">");
		output.append("<tr STYLE=\"margin-bottom: 5px;\" width=\"100px\" bgcolor=\"#084B8A\"><td  height =\"30px\" width=\"100px\"colspan=\"4\"><font face=\"Tempus Sans ITC\" size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Search Results</b></p></font></td></tr>");
		getSearchResults();
		output.append("</table>");
		output.append("</html>");

		return output.toString();

	}

	/**
	 * This is used for Testing
	 * 
	 * @return ArrayList<Task>
	 */
	public ArrayList<Task> getSearchList() {
		ArrayList<Task> searchList = data.getSearched(searchedText);
		return searchList;
	}

}
