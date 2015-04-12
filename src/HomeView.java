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

	public HomeView() {
		update();
	}

	//updates the view with the latest list
	public void update() {
		Data data = Data.getInstance();
		this.today = data.getToday();
		this.upcoming = data.getUpcoming();
		this.someday = data.getSomeday();
	}

	//gets the task information, such as start time, date and etc
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

	//show calls this method to get the tasks categorized under today
	protected void getToday() throws BadLocationException {
		i = 0;
		for (Task task : today) {
		 formatTasksForToday(task);
		}
	}

	//pass the task to methods depending on their type. Different way of displaying for each type of task
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

	//timed task is formatted here for displaying. 
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
			formatOverdueTasks(taskNo,tasks);
		} else {
			formatTasks(taskNo,startEndTime,tasks);
		}
		
	}

	//deadline task is formatted here for displaying
	private void formatDeadlineTaskToday(Task task, String taskNo) throws BadLocationException {
		String endTimeFormatted = "";
		String tasks = taskDes;
		endTimeFormatted = endTime.format(formatTime).replace("AM", "am")
				.replace("PM", "pm").replace(".00", "");
		
		endTimeFormatted = endTimeFormatted.toString().replaceAll("\\[", "")
				.replaceAll("\\]", " -");
		if (task.isOverdue()) {
			formatOverdueTasks(taskNo,tasks);
		} else {
			formatTasks(taskNo,endTimeFormatted,tasks);
		}
		
	}
	
	//upcoming tasks are sent for formatting here
	protected void getUpcoming() throws BadLocationException {

		for (Task task : upcoming) {
			if (i < upcomingLimit) {
				getTasksForUpcoming(task);
			}
		}
	}

	//tasks are categorized into deadline task and timedtask and sent for formatting
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

	
	//the startdate is formatted here and formatTasks is called to append the tasks to the pane
	private void formatTimedTaskUpcoming(Task task, String taskNo) throws BadLocationException {
		String tasks = taskDes;
		String startDateFormatted = "";
		startDateFormatted = startDate.format(formatter);
		startDateFormatted = startDateFormatted.replaceAll("\\[", "").replaceAll("\\]", "-");
		formatTasks(taskNo, startDateFormatted,tasks);
		
	}

	//the endDate is formatted here and formatTasks is called to append the tasks to the pane
	private void formatDeadlineTaskUpcoming(Task task, String taskNo) throws BadLocationException {
		String tasks = taskDes;
		String endDateFormatted ="";
		 endDateFormatted= endDate.format(formatter);
		 endDateFormatted = endDateFormatted.replaceAll("\\[", "").replaceAll("\\]", "-");

			formatTasks(taskNo, endDateFormatted,tasks);
		
	}
	
	//normal tasks (non-overdue) are given colour and are sent to be appended to the pane here
	private void formatTasks(String taskNo, String dateTimeFormatted, String tasks) throws BadLocationException {

		appendTasks("#848484", taskNo, 1);
		appendTasks("#FFFFFF", "!", 2);
		appendTasks("#01A9DB", dateTimeFormatted, 3);
		appendTasks("#0A1B2A", tasks, 4);
	}

	//overdue tasks are given colour and are sent to be appended to the pane here
	private void formatOverdueTasks(String taskNo, String tasks) throws BadLocationException {

		String endDateFormatted="";
		if (!(endDate.equals(LocalDate.now()))) {
			endDateFormatted = endDate.format(formatter);
			endDateFormatted= endDateFormatted.replaceAll("\\[", "").replaceAll("\\]", "-");
		}
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FF0000", "!", 2);
		appendTasks("#01A9DB", endDateFormatted, 3);
		appendTasks("#0A1B2A", tasks, 4);
	}

	
	//tasks under someday are sent for coloring here
	protected void getSomeday() throws BadLocationException {
		for (Task task : someday) {
			if (i<somedayLimit) {
				i++;
				String taskNo = "     " + i + ".   ";
				String tasks = task.toString() + "\n";
				formatSomedayTasks(taskNo,tasks);
			}
		}
	}

	//someday tasks are coloured and sent to be appended to the pane
	private void formatSomedayTasks(String taskNo, String tasks) throws BadLocationException {
		appendTasks("#848484", taskNo, 1);
		appendTasks("#FFFFFF", "!", 2);
		appendTasks("#0A1B2A", tasks, 5);
		
	}

	//tasks are appended to the string output
	public void appendTasks(String textColour, String s, int row)
			throws BadLocationException {
		if (row == 1) {
			output.append("<tr width=\"100px\" >" + "<td valign=\"top\""
					+ " width=\"40px\"><font size=\"4\" color=\"" + textColour
					+ "\"><p align=\"right\"><b>" + s + "</b></p></font></td>");
		} else if (row == 2) {
			output.append("<td valign=\"top\" width=\"1px\"><font size=\"4.5\" color=\""
					+ textColour
					+ "\"><p align=\"center\"><b>"
					+ s
					+ "</b></p></font></td>");
		} else if (row == 3) {
			output.append("<td valign=\"top\" width=\"180px\"><font face=\"Rockwell\" size=\"3.5\" color=\""
					+ textColour
					+ "\"><p align=\"left\"><b>"
					+ s
					+ "</b></p></font></td>");
		} else if (row == 4) {
			output.append("<td valign=\"top\" width=\"420px\"><font face=\"Eras Demi ITC\" size=\"3.5\" color=\""
					+ textColour
					+ "\"><p align=\"left\">"
					+ s
					+ "</p></font></td></tr>");
		} else if (row == 5) {
			output.append("<td valign=\"top\" colspan=\"420px\" width=\"420px\"><font face=\"Eras Demi ITC\" size=\"3.5\" color=\""
					+ textColour
					+ "\"><p align=\"left\">"
					+ s
					+ "</p></font></td></tr>");
		}

	}

	//called by UI to show the tasks under home
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

	public Task getTask(int numbering) throws IndexOutOfBoundsException {
		int index = numbering - 1;

		int todaySize = today.size();
		int dateSize = todaySize + upcoming.size();
	//	int allSize = dateSize + someday.size();

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

	@Override
	public ArrayList<Task> getList() {
		List<Task> combinedList = new ArrayList<Task>();
		combinedList.addAll(today);
		combinedList.addAll(upcoming);
		combinedList.addAll(someday);

		return (ArrayList<Task>) combinedList;
	}

}
