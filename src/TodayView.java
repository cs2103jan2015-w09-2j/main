//@author A0112715R

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.text.BadLocationException;

public class TodayView extends SingleView implements View {
	private String taskDes;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("EEE, dd MMMM");
	private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern(
			"h:mm a", Locale.US);
	private StringBuilder output = new StringBuilder();
	private int page = 1;
	private int taskNo;
	private int taskLimit = 15;
	Display display;

	/**
	 * Updates the list of tasks
	 */
	@Override
	public void update() {
		setList(data.getToday());
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
				startTime = task.getStart().toLocalTime();
			}
			endDate = task.getEnd().toLocalDate();
			endTime = task.getEnd().toLocalTime();
		}
	}

	/**
	 * Supports paging. Sets the tasks to be shown for each "page"
	 * 
	 * @return List<Task>
	 */
	private List<Task> getTasksForPage() {
		if (page == 0) {
			page = 1;
		}
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
					page = 1;
					getTasksForPage();
				}
			}
		} else {
			tasksForPage = getList();
		}

		taskNo = startTaskNo;
		return tasksForPage;
	}

	/**
	 * Gets list of today task and sends for formatting
	 * 
	 * @throws BadLocationException
	 */
	private void getToday() throws BadLocationException {
		for (Task task : getTasksForPage()) {
			taskNo++;
			getTaskInfo(task);
			if (task.isDeadlineTask()) {
				formatDeadlineTask(task, taskNo);

			} else {
				formatTimedTasks(task, taskNo);

			}
		}
	}

	private void formatTimedTasks(Task task, int taskNo)
			throws BadLocationException {
		String taskNoFormatted = "" + taskNo + ".   ";
		String tasks = taskDes;
		String timeToDisplay = formatStartEndTimeToDisplay();
		boolean isCommandExecuted = (display.getCommand().equals(
				COMMAND_TYPE.DELETE)
				|| display.getCommand().equals(COMMAND_TYPE.ADD)
				|| display.getCommand().equals(COMMAND_TYPE.DONE) || display
				.getCommand().equals(COMMAND_TYPE.EDIT));
		
		if (isCommandExecuted && taskNo == display.getViewIndex()) {
			formatTaskAccdCommand(taskNoFormatted, timeToDisplay, tasks);
		} else if (task.isOverdue()) {
			formatOverdueTasks(taskNoFormatted, timeToDisplay, tasks);
		} else {
			formatTasks(taskNoFormatted, timeToDisplay, tasks);
		}

	}


	private String formatStartEndTimeToDisplay() {
		String timeToDisplay = startTime.format(formatTime).replace("AM", "am")
				.replace("PM", "pm").replace(".00", "")
				+ " - "
				+ endTime.format(formatTime).replace("AM", "am")
						.replace("PM", "pm").replace(".00", "");

		timeToDisplay = timeToDisplay.toString().replaceAll("\\[", "")
				.replaceAll("\\]", " -");

		return timeToDisplay;

	}

	private void formatDeadlineTask(Task task, int taskNo)
			throws BadLocationException {
		String tasks = taskDes;
		String taskNoFormatted = "" + taskNo + ".   ";
		String endTimeToDisplay = formatEndTimeToDisplay();
		boolean isCommandExecuted = (display.getCommand().equals(
				COMMAND_TYPE.DELETE)
				|| display.getCommand().equals(COMMAND_TYPE.ADD)
				|| display.getCommand().equals(COMMAND_TYPE.DONE) || display
				.getCommand().equals(COMMAND_TYPE.EDIT));
		
		if (isCommandExecuted && taskNo == display.getViewIndex()) {
			formatTaskAccdCommand(taskNoFormatted, endTimeToDisplay, tasks);
		} else if (task.isOverdue()) {
			formatOverdueTasks(taskNoFormatted, endTimeToDisplay, tasks);
		} else {
			formatTasks(taskNoFormatted, endTimeToDisplay, tasks);
		}

	}

	private void formatTaskAccdCommand(String taskNo, String endTimeToDisplay,
			String tasks) throws BadLocationException {
		if(display.getCommand().equals(COMMAND_TYPE.DELETE)){
			formatDeletedTask(taskNo, endTimeToDisplay, tasks);
		}else if(display.getCommand().equals(COMMAND_TYPE.ADD)){
			formatAddTask(taskNo, endTimeToDisplay, tasks);
		}else if(display.getCommand().equals(COMMAND_TYPE.DONE)){
			formatDoneTask(taskNo, endTimeToDisplay, tasks);
		}
		else if(display.getCommand().equals(COMMAND_TYPE.EDIT)){
			formatEditTask(taskNo, endTimeToDisplay, tasks);
		}
		
		
	}
	private void formatDeletedTask(String taskNo, String dateTimeToDisplay,
			String tasks) throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FF0000", "", 2);
		appendTasks("#848484", "<strike>" + dateTimeToDisplay + "</strike>", 3);
		appendTasks("#848484", "<strike>" + tasks + "</strike>", 4);
	}

	private void formatEditTask(String taskNo, String dateTimeToDisplay,
			String tasks) {

		
	}

	private void formatDoneTask(String taskNo, String dateTimeToDisplay,
			String tasks) throws BadLocationException {

		appendTasks("#848484", taskNo, 1);
		appendTasks("#FF0000", "", 2);
		appendTasks("#00A300",  dateTimeToDisplay, 3);
		appendTasks("#00A300", tasks , 4);
	}

	private void formatAddTask(String taskNo, String dateTimeToDisplay,
			String tasks) throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FF0000", "", 2);
		appendTasks("#B8005C",  "<font size=\"4\">"+dateTimeToDisplay+"</font>", 3);
		appendTasks("#B8005C", "<font size=\"4\">"+tasks+"</font>" , 4);
		
	}

	private String formatEndTimeToDisplay() {
		String timeToDisplay = endTime.format(formatTime).replace("AM", "am")
				.replace("PM", "pm").replace(".00", "");
		timeToDisplay = timeToDisplay.toString().replaceAll("\\[", "")
				.replaceAll("\\]", " -");
		return timeToDisplay;
	}

	private void formatTasks(String taskNo, String timeToDisplay, String tasks)
			throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FFFFFF", "!", 2);
		appendTasks("#01A9DB", timeToDisplay, 3);
		appendTasks("#0A1B2A", tasks, 4);
	}

	private void formatOverdueTasks(String taskNo, String dateTimeToDisplay,
			String tasks) throws BadLocationException {

		if (!(endDate.equals(LocalDate.now()))) {
			dateTimeToDisplay = endDate.format(formatter);
			dateTimeToDisplay = dateTimeToDisplay.replaceAll("\\[", "")
					.replaceAll("\\]", "-");
		}

		appendTasks("#848484", taskNo, 1);
		appendTasks("#FF0000", "!", 2);
		appendTasks("#01A9DB", dateTimeToDisplay, 3);
		appendTasks("#0A1B2A", tasks, 4);
	}

	/**
	 * Appends to Stringbuilder output
	 * 
	 * @param textColour
	 *            ,text,row
	 * @throws BadLocationException
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
					+ textColour
					+ "\"><p align=\"left\"><b>"
					+ text
					+ "</b></p></font></td>");
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
	 * @throws BadLocationException
	 */
	@Override
	public String show() throws BadLocationException{
		output = new StringBuilder();
		display = Display.getInstance();
		page = display.getPaging();

		output.append("<html>");
		output.append("<table  STYLE=\"margin-bottom: 15px;\" cellpadding=\"3px\" cellspacing=\"0px\" width=\"100%\">");
		output.append("<tr STYLE=\"margin-bottom: 5px;\" width=\"100px\" bgcolor=\"#084B8A\"><td  height =\"30px\" width=\"100px\"colspan=\"4\"><font face=\"Tempus Sans ITC\" size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Today</b></p></font></td></tr>");
		getToday();
		output.append("</table>");
		output.append("</table></html>");
		return output.toString();

	}

	/**
	 * This is used for Testing
	 * 
	 * @return ArrayList<Task>
	 */
	public ArrayList<Task> getTodayList() {
		ArrayList<Task> todayList = data.getToday();
		return todayList;
	}

}
