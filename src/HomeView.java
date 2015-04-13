//@author A0112715R
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.text.BadLocationException;

public class HomeView implements View {
	private ArrayList<Task> today;
	private ArrayList<Task> upcoming;
	private ArrayList<Task> someday;
	private int i = 0;
	private String taskDes;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
			"EEE, dd MMM yyyy", Locale.US);
	private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h.mma",
			Locale.US);
	private StringBuilder output = new StringBuilder();
	private static int todayLimit =6;
	private static int upcomingLimit = 8;
	private static int somedayLimit = 10;

	/**
	 * Updates the view with the latest list
	 */

	//@author A0111867A
	public HomeView() {
		update();
	}

	/**
	 * Sets the list of tasks for each category
	 */
	//@author A0111867A
	public void update() {
		Data data = Data.getInstance();
		this.today = data.getToday();
		this.upcoming = data.getUpcoming();
		this.someday = data.getSomeday();
	}

	/**
	 * Gets the task based on the number
	 * @param numbering
	 * @return Task
	 */
	public Task getTask(int numbering) throws IndexOutOfBoundsException {
		int index = numbering - 1;

		int todaySize = Math.min(today.size(), todayLimit);
		int dateSize = Math.min(todaySize + upcoming.size(), upcomingLimit);
		int allSize = Math.min(dateSize + someday.size(), somedayLimit);

		if (index > -1 && index < todaySize) {
			return today.get(index);
		} 
		else if (index < dateSize) {
			int upcomingIndex = index - todaySize;
			return upcoming.get(upcomingIndex);
		} 
		else if(index < allSize){
			int somedayIndex = index - dateSize;
			return someday.get(somedayIndex);
		}
		else{
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * Sets the combined list. Used for testing.
	 * 
	 * @return ArrayList<Task>
	 */
	@Override
	public ArrayList<Task> getList() {
		List<Task> combinedList = new ArrayList<Task>();
		combinedList.addAll(today);
		combinedList.addAll(upcoming);
		combinedList.addAll(someday);

		return (ArrayList<Task>) combinedList;
	}
	
	//@author A0112715R
	/**
	 * Gets the task information and initializes the variables
	 * @param task
	 */
	//@author A0112715R
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
	 * Gets the list of tasks under today and sends it for formatting
	 */
	//@author A0112715R
	private void getToday() throws BadLocationException {
		i = 0;
		for (Task task : today) {
		 formatTasksForToday(task);
		}
	}

	/**
	 * Sends the different types of tasks under today for formatting
	 * @param task
	 */
	//@author A0112715R
	private void formatTasksForToday(Task task) throws BadLocationException {
		if (i < todayLimit) {
			i++;
			getTaskInfo(task);
			String taskNo = "" + i + ".   ";
			if (task.isDeadlineTask()) {
				formatDeadlineTaskToday(task,taskNo);

			} else {
				formatTimedTaskToday(task,taskNo);

			}
		}
	}
	
	/**
	 * Formats timed task under today category
	 * @param task, taskNo : the number of the task
	 */
	//@author A0112715R
	private void formatTimedTaskToday(Task task, String taskNo) throws BadLocationException {

		String tasks = taskDes;
		String startTimeFormatted= startTime.format(formatTime).replace("AM", "am")
				.replace("PM", "pm").replace(".00", "");
		String endTimeFormatted = endTime.format(formatTime).replace("AM", "am")
						.replace("PM", "pm").replace(".00", "");
		String startEndTime =startTimeFormatted+"-"+endTimeFormatted;
		
		startEndTime = startEndTime.toString().replaceAll("\\[", "")
				.replaceAll("\\]", " -");
		

		if (task.isOverdue()) {
			formatOverdueTasks(taskNo,tasks,startEndTime);
		} else {
			formatTasks(taskNo,startEndTime,tasks);
		}
		
	}

	/**
	 * Gets the list of tasks from file, puts them into an ArrayList
	 * @param task, taskNo
	 */
	//@author A0112715R
	private void formatDeadlineTaskToday(Task task, String taskNo) throws BadLocationException {
		String endTimeFormatted = "";
		String tasks = taskDes;
		endTimeFormatted = endTime.format(formatTime).replace("AM", "am")
				.replace("PM", "pm").replace(".00", "");
		
		endTimeFormatted = endTimeFormatted.toString().replaceAll("\\[", "")
				.replaceAll("\\]", " -");
		if (task.isOverdue()) {
			formatOverdueTasks(taskNo,tasks,endTimeFormatted);
		} else {
			formatTasks(taskNo,endTimeFormatted,tasks);
		}
		
	}
	
	/**
	 * Gets the list of upcoming tasks and sends them for formatting
	 */
	private void getUpcoming() throws BadLocationException {

		for (Task task : upcoming) {
			if (i < upcomingLimit) {
				getTasksForUpcoming(task);
			}
		}
	}

	/**
	 * Different types of tasks are sent for formatting
	 * @param task
	 */
	//@author A0112715R
	private void getTasksForUpcoming(Task task) throws BadLocationException {
		i++;
		getTaskInfo(task);
		String taskNo = "     " + i + ".   ";
		if (task.isDeadlineTask()) {
			formatDeadlineTaskUpcoming(task,taskNo);
		}

		else {
			formatTimedTaskUpcoming(task,taskNo);
		}
		
	}

	/**
	 * Formats Timed Tasks under the upcoming category
	 * @param task, taskNo
	 */
	//@author A0112715R
		private void formatTimedTaskUpcoming(Task task, String taskNo) throws BadLocationException {
		String tasks = taskDes;
		String startDateFormatted = "";
		startDateFormatted = startDate.format(formatter);
		startDateFormatted = startDateFormatted.replaceAll("\\[", "").replaceAll("\\]", "-");
		formatTasks(taskNo, startDateFormatted,tasks);
		
	}

		/**
		 * Formats deadline tasks under upcoming category
		 * @param task, taskNo
		 */
		//@author A0112715R
	private void formatDeadlineTaskUpcoming(Task task, String taskNo) throws BadLocationException {
		String tasks = taskDes;
		String endDateFormatted ="";
		 endDateFormatted= endDate.format(formatter);
		 endDateFormatted = endDateFormatted.replaceAll("\\[", "").replaceAll("\\]", "-");

			formatTasks(taskNo, endDateFormatted,tasks);
		
	}
	/**
	 * Format Non-overdue tasks
	 * @param taskNo, dateTimeFormatted, tasks
	 */
	//@author A0112715R
	private void formatTasks(String taskNo, String dateTimeFormatted, String tasks) throws BadLocationException {

		appendTasks("#848484", taskNo, 1);
		appendTasks("#FFFFFF", "!", 2);
		appendTasks("#01A9DB", dateTimeFormatted, 3);
		appendTasks("#0A1B2A", tasks, 4);
	}

	/**
	 * Format overdue tasks
	 * @param endTimeFormatted2 
	 * @param
	 */
	//@author A0112715R
	private void formatOverdueTasks(String taskNo, String tasks, String endTimeFormatted) throws BadLocationException {
		String endDateTimeFormatted=endTimeFormatted;
		
		if (!(endDate.equals(LocalDate.now()))) {
			endDateTimeFormatted = endDate.format(formatter);
			endDateTimeFormatted= endDateTimeFormatted.replaceAll("\\[", "").replaceAll("\\]", "-");
		}
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FF0000", "!", 2);
		appendTasks("#01A9DB", endDateTimeFormatted, 3);
		appendTasks("#0A1B2A", tasks, 4);
	}

	/**
	 * Gets the list of tasks from someday list and sends them for formatting
	 */
	//@author A0112715R
	private void getSomeday() throws BadLocationException {
		for (Task task : someday) {
			if (i<somedayLimit) {
				i++;
				String taskNo = "     " + i + ".   ";
				String tasks = task.toString() + "\n";
				formatSomedayTasks(taskNo,tasks);
			}
		}
	}

	/**
	 * Format tasks under someday 
	 * @param taskNo, tasks
	 */
	//@author A0112715R
	private void formatSomedayTasks(String taskNo, String tasks) throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FFFFFF", "!", 2);
		appendTasks("#0A1B2A", tasks, 5);
		
	}

	/**
	 * Gets the list of tasks from file, puts them into an ArrayList
	 * @param textColour, text, row : row number for table
	 */
	//@author A0112715R
	public void appendTasks(String textColour, String text, int row)
			throws BadLocationException {
		if (row == 1) {
			output.append("<tr width=\"100px\" >" + "<td valign=\"top\""
					+ " width=\"40px\"><font size=\"4\" color=\"" + textColour
					+ "\"><p align=\"right\"><b>" + text + "</b></p></font></td>");
		} else if (row == 2) {
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
		} else if (row == 5) {
			output.append("<td valign=\"top\" colspan=\"420px\" width=\"420px\"><font face=\"Eras Demi ITC\" size=\"3.5\" color=\""
					+ textColour
					+ "\"><p align=\"left\">"
					+ text
					+ "</p></font></td></tr>");
		}

	}
	
	/**
	 * Forms the html to show on the JTextPane in UserInterface class
	 * 
	 * @return String
	 */
	//@author A0112715R
	public String show() throws BadLocationException {
		output = new StringBuilder();
		output.append("<html>");
		output.append("<table  STYLE=\"margin-bottom: 15px;\" cellpadding=\"3px\" cellspacing=\"0px\" width=\"100%\">");
		output.append("<tr STYLE=\"margin-bottom: 5px;\" width=\"100px\" bgcolor=\"#084B8A\"><td  height =\"30px\" width=\"100px\"colspan=\"4\"><font face=\"Tempus Sans ITC\" size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Today</b></p></font></td></tr>");
		getToday();
		output.append("</table>");

		output.append("<table STYLE=\"margin-bottom: 15px;\" cellpadding=\"3px\" cellspacing=\"0px\" width=\"100%\">");
		output.append("<tr STYLE=\"margin-bottom: 5px;\" width=\"100px\" bgcolor=\"#084B8A\"><td height =\"30px\" width=\"100px\"colspan=\"4\"><font face=\"Tempus Sans ITC\" size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Upcoming </b></p></font></td></tr>");
		getUpcoming();
		output.append("</table>");

		output.append("<table STYLE=\"margin-bottom: 15px;\" cellpadding=\"3px\" cellspacing=\"0px\" width=\"100%\">");
		output.append("<tr STYLE=\"margin-bottom: 5px;\" width=\"100px\" bgcolor=\"#084B8A\"><td height =\"30px\" width=\"100px\"colspan=\"4\"><font face=\"Tempus Sans ITC\" size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Someday </b></p></font></td></tr>");
		getSomeday();
		output.append("</table>");

		output.append("</html>");

		return output.toString();

	}

}
