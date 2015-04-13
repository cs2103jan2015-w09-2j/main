//@author A0112715R
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import javax.swing.text.BadLocationException;

public class UpcomingView extends SingleView implements View {

	private String taskDes;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
			"EEE, dd MMM yyyy", Locale.US);
	private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h.mma",
			Locale.US);
	private StringBuilder output;
	private int page = 1;
	private int i = 0;
	private int taskLimit = 5;
	private String INVALID_PAGE_MESSAGE = "The page number entered is invalid";
	private String CLASS_MESSAGE = "UpcomingView";
	Display display = Display.getInstance();
	OneTagLogger logger = OneTagLogger.getInstance();

	/**
	 * Updates the list of tasks
	 */
	@Override
	public void update() {
		Data data = Data.getInstance();

		setList(data.getUpcoming());
	}

	/**
	 * Supports paging. Sets the tasks to be shown for each "page"
	 * 
	 * @return List<Task>
	 */
	private List<Task> getTasksForPage() {
		List<Task> tasksForPage = new ArrayList<Task>();
		int startTaskNo = 0;

		if (getList().size() >= taskLimit) {
			startTaskNo = (page - 1) * taskLimit;
			int endTaskNo = startTaskNo + taskLimit;
			try {
				tasksForPage = getList().subList(startTaskNo, endTaskNo);
			} catch (IndexOutOfBoundsException e) {
				try {
					tasksForPage = getList().subList(startTaskNo,
							getList().size());
				} catch (IllegalArgumentException e1) {
					logger.log(Level.WARNING, CLASS_MESSAGE,
							INVALID_PAGE_MESSAGE);
				}
			}
		} else {
			tasksForPage = getList();
		}
		i = startTaskNo;
		return tasksForPage;
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
	 * Gets list of upcoming task and sends for formatting
	 */
	private void getUpcoming() throws BadLocationException {
		for (Task task : getTasksForPage()) {
			i++;
			getTaskInfo(task);
			if (task.isDeadlineTask()) {
				String tasks = taskDes;
				String taskNoFormatted = "     " + i + ".   ";
				String endDateTime = formatTimeDate();

				boolean isCommandExecuted = (display.getCommand().equals(
						COMMAND_TYPE.DELETE)
						|| display.getCommand().equals(COMMAND_TYPE.ADD)
						|| display.getCommand().equals(COMMAND_TYPE.DONE) || display
						.getCommand().equals(COMMAND_TYPE.EDIT));

				if (isCommandExecuted && i == display.getViewIndex()) {
					formatTaskAccdCommand(taskNoFormatted, endDateTime,"", tasks);
				} else {
					formatTasks(taskNoFormatted, endDateTime,"", tasks);
				}
			}

			else {
				String tasks = taskDes;
				String taskNoFormatted = "     " + i + ".   ";
				String dateToDisplay = formatStartEndDate();
				String timeToDisplay = formatStartEndTime();
	
				boolean isCommandExecuted = (display.getCommand().equals(
						COMMAND_TYPE.DELETE)
						|| display.getCommand().equals(COMMAND_TYPE.ADD)
						|| display.getCommand().equals(COMMAND_TYPE.DONE) || display
						.getCommand().equals(COMMAND_TYPE.EDIT));

				if (isCommandExecuted && i == display.getViewIndex()) {
					formatTaskAccdCommand(taskNoFormatted, dateToDisplay, timeToDisplay,
							tasks);
				} else {
					formatTasks(taskNoFormatted, dateToDisplay, timeToDisplay,
							tasks);
				}
			}
		}
	}

	private void formatTaskAccdCommand(String taskNo, String dateToDisplay,
			String timeToDisplay, String tasks) throws BadLocationException {
		if (display.getCommand().equals(COMMAND_TYPE.DELETE)) {
			formatDeletedTask(taskNo, dateToDisplay,timeToDisplay, tasks);
		} else if (display.getCommand().equals(COMMAND_TYPE.ADD)) {
			formatAddTask(taskNo, dateToDisplay,timeToDisplay, tasks);
		} else if (display.getCommand().equals(COMMAND_TYPE.DONE)) {
			formatDoneTask(taskNo, dateToDisplay,timeToDisplay, tasks);
		} else if (display.getCommand().equals(COMMAND_TYPE.EDIT)) {
			formatEditTask(taskNo, dateToDisplay,timeToDisplay, tasks);
		}
	}

	private void formatDeletedTask(String taskNo, String dateToDisplay,String timeToDisplay,
			String tasks) throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FF0000", "", 2);
		appendTasks("#848484", "<strike>" + dateToDisplay + timeToDisplay + "</strike>", 3);
		appendTasks("#848484", "<strike>" + tasks + "</strike>", 4);
	}

	private void formatEditTask(String taskNo, String dateToDisplay,String timeToDisplay,
			String tasks) throws BadLocationException {

	}

	private void formatDoneTask(String taskNo, String dateToDisplay,String timeToDisplay,
			String tasks) throws BadLocationException {

		appendTasks("#848484", taskNo, 1);
		appendTasks("#FF0000", "", 2);
		appendTasks("#00A300",  dateToDisplay + timeToDisplay , 3);
		appendTasks("#00A300", tasks, 4);
	}

	private void formatAddTask(String taskNo, String dateToDisplay,String timeToDisplay,
			String tasks) throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FF0000", "", 2);
		appendTasks("#B8005C", "<font size=\"4\">" +  dateToDisplay + timeToDisplay 
				+ "</font>", 3);
		appendTasks("#B8005C", "<font size=\"4\">" + tasks + "</font>", 4);

	}

	private void formatTasks(String taskNo, String dateToDisplay,String timeToDisplay,
			String tasks) throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FFFFFF", "!", 2);
		appendTasks("#01A9DB", dateToDisplay + "<font color=\"#1F3D7A\">" + timeToDisplay + "</font>", 3);
		appendTasks("#0A1B2A", tasks, 4);
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

		String timeToDisplay = "<p align=\"left\">"
				+ startTimeToDisplay + " - " + endTimeToDisplay + "</p>";

		return timeToDisplay;
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
		return dateToDisplay;
	}

	private String formatTimeDate() {
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
			output.append("<td valign=\"top\" width=\"180px\"><font face=\"Rockwell\" size=\"3.5\" color=\""
					+ textColour + "\"><b>" + text + "</b></font></td>");
		} else if (row == 4) {
			output.append("<td valign=\"top\" width=\"420px\"><font face=\"Eras Demi ITC\" size=\"3.5\" color=\""
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
		display = Display.getInstance();
		page = display.getPaging();

		output.append("<html>");

		output.append("<table STYLE=\"margin-bottom: 15px;\" cellpadding=\"7px\" cellspacing=\"0px\" width=\"100%\">");
		output.append("<tr STYLE=\"margin-bottom: 5px;\" width=\"100px\" bgcolor=\"#084B8A\"><td height =\"30px\" width=\"100px\"colspan=\"4\"><font face=\"Tempus Sans ITC\" size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Upcoming </b></p></font></td></tr>");
		getUpcoming();
		output.append("</table>");

		output.append("</html>");

		return output.toString();

	}

	/**
	 * This is used for Testing
	 * 
	 * @return ArrayList<Task>
	 */
	public ArrayList<Task> getUpcomingList() {
		ArrayList<Task> upcomingList = data.getUpcoming();
		return upcomingList;
	}

}
