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

	public HomeView() {
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
			getTasksForToday(task);
		}
	}

	private void getTasksForToday(Task task) throws BadLocationException {
		if (i < 4) {
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

	private void formatTimedTaskToday(Task task, String taskNo) throws BadLocationException {
		String startDateTime="";
		String tasks = taskDes;
		startDateTime= startTime.format(formatTime).replace("AM", "am")
				.replace("PM", "pm").replace(".00", "")
				+ " - "
				+ endTime.format(formatTime).replace("AM", "am")
						.replace("PM", "pm").replace(".00", "");
		startDateTime = startDateTime.toString().replaceAll("\\[", "")
				.replaceAll("\\]", " -");

		if (task.isOverdue()) {
			if (!(endDate.equals(LocalDate.now()))) {
				startDateTime = endDate.format(formatter);
				startDateTime= startDateTime.replaceAll("\\[", "").replaceAll("\\]", "-");
			}
			appendTasks("#848484", taskNo, 1);
			appendTasks("#FF0000", "!", 2);
			appendTasks("#01A9DB", startDateTime, 3);
			appendTasks("#0A1B2A", tasks, 4);
		} else {
			appendTasks("#848484", taskNo, 1);
			appendTasks("#FFFFFF", "!", 2);
			appendTasks("#01A9DB", startDateTime, 3);
			appendTasks("#0A1B2A", tasks, 4);
		}
		
	}

	private void formatDeadlineTaskToday(Task task, String taskNo) throws BadLocationException {
		String endDateTime = "";
		String tasks = taskDes;
		endDateTime = endTime.format(formatTime).replace("AM", "am")
				.replace("PM", "pm").replace(".00", "");
		endDateTime = endDateTime.toString().replaceAll("\\[", "")
				.replaceAll("\\]", " -");

		if (task.isOverdue()) {
			if (!(endDate.equals(LocalDate.now()))) {
				endDateTime = endDate.format(formatter);
				endDateTime = endDateTime.replaceAll("\\[", "").replaceAll("\\]", "-");
			}
			appendTasks("#848484", taskNo, 1);
			appendTasks("#FF0000", "!", 2);
			appendTasks("#01A9DB", endDateTime, 3);
			appendTasks("#0A1B2A", tasks, 4);
		} else {
			appendTasks("#848484", taskNo, 1);
			appendTasks("#FFFFFF", "!", 2);
			appendTasks("#01A9DB", endDateTime, 3);
			appendTasks("#0A1B2A", tasks, 4);
		}
		
	}

	protected void getUpcoming() throws BadLocationException {

		for (Task task : upcoming) {
			if (i < 8) {
				getTasksForUpcoming(task);
			}
		}
	}

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

	private void formatTimedTaskUpcoming(Task task, String taskNo) throws BadLocationException {
		String tasks = taskDes;
		String startDateTime = "";
		startDateTime = startDate.format(formatter);
		startDateTime = startDateTime.replaceAll("\\[", "").replaceAll("\\]", "-");
	
			appendTasks("#848484", taskNo, 1);
			appendTasks("#FFFFFF", "!", 2);
			appendTasks("#01A9DB", startDateTime, 3);
			appendTasks("#0A1B2A", tasks, 4);
		
	}

	private void formatDeadlineTaskUpcoming(Task task, String taskNo) throws BadLocationException {
		String tasks = taskDes;
		String endDateTime ="";
		 endDateTime= endDate.format(formatter);
		 endDateTime = endDateTime.replaceAll("\\[", "").replaceAll("\\]", "-");

			appendTasks("#848484", taskNo, 1);
			appendTasks("#FFFFFF", "!", 2);
			appendTasks("#01A9DB", endDateTime, 3);
			appendTasks("#0A1B2A", tasks, 4);
		
	}

	protected void getSomeday() throws BadLocationException {
		for (Task task : someday) {
			if (i < 10) {
				i++;
				String taskNo = "     " + i + ".   ";
				String tasks = task.toString() + "\n";
				if (task.getIsCompleted()) {
					appendTasks("#848484", taskNo, 1);
					appendTasks("#FFFFFF", "!", 2);
					appendTasks("#04B45F", tasks, 5);
				} else {
					appendTasks("#848484", taskNo, 1);
					appendTasks("#FFFFFF", "!", 2);
					appendTasks("#0A1B2A", tasks, 5);
				}
			}
		}
	}


	// protected void isTaskOverdue(Task task) {
	// isOverdue = false;
	// LocalDateTime now = LocalDateTime.now();
	// LocalDateTime endDateTime = task.getEnd();
	//
	// if (endDateTime.isBefore(now)) {
	// isOverdue = true;
	// }
	// }

	public void appendTasks(String textColour, String s, int row)
			throws BadLocationException {
		if (row == 1) {
			output.append("<tr width=\"100px\" >" + "<td valign=\"top\""
					+ " width=\"40px\"><font size=\"4\" color=\"" + textColour
					+ "\"><p align=\"right\"><b>" + s + "</b></p></font></td>");
		} else if (row == 2) {
			// output.append("<td width=\"1px\"><img src=\"alert.jpg\"></td>");
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
