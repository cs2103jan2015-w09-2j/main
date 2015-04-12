//@author A0112715R
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

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
	private int i=0;
	private int page=1;
	
	@Override
	public void update() {
		Data data = Data.getInstance();

		setList(data.getCompleted());
	}
	
	protected ArrayList<Task> getTasksForPage() {
		ArrayList<Task> tasksForPage = new ArrayList<Task>();
		int startTaskNo = 0;
		if (page != 1) {
			startTaskNo = (page - 1) * 5;
		}
		i = startTaskNo;
		int endTaskNo = startTaskNo + 5;
		if (getList().size() > 14) {
			try {
				for (Task task : getList().subList(startTaskNo, endTaskNo)) {
					tasksForPage.add(task);
				}
			} catch (IndexOutOfBoundsException e) {
				for (Task task : getList().subList(startTaskNo,
						getList().size())) {
					tasksForPage.add(task);
				}
			}
		} else {
			tasksForPage = getList();
		}
		return tasksForPage;
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

	protected void getCompletedTasks() throws BadLocationException {
		
		for (Task task : getList()) {
				i++;
				getTaskInfo(task);
				String taskNo = "     " + i + ".   ";
				if (task.isDeadlineTask()) {
					String tasks = taskDes;
					String endDateToDisplay = endDate.format(formatter);
					String endTimeToDisplay = endTime.format(formatTime);
					endDateToDisplay = endDateToDisplay.replaceAll("\\[", "").replaceAll("\\]","");
					endTimeToDisplay = endTimeToDisplay.replaceAll("\\[", "").replaceAll("\\]", "");
					endTimeToDisplay = endTimeToDisplay.replace("AM", "am").replace("PM", "pm").replace(".00", "");

					String endDateTime = "<p align=\"left\">"+endDateToDisplay+"</p>"+"<p align=\"left\"><font color=\"#1F3D7A\">"+endTimeToDisplay+"</font></p>";
					
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FFFFFF", "!", 2);
						appendTasks("#01A9DB", endDateTime, 3);
						appendTasks("#0A1B2A", tasks, 4);
				}
				else {
					String tasks = taskDes;
					
					String dateToDisplay ="---";
					String timeToDisplay ="---";
					
					if(!task.isFloatingTask()){
					String startDateToDisplay = startDate.format(formatter);
					String startTimeToDisplay = startTime.format(formatTime);
					startDateToDisplay = startDateToDisplay.replaceAll("\\[", "").replaceAll("\\]","");
					startTimeToDisplay = startTimeToDisplay.replaceAll("\\[", "").replaceAll("\\]", "");
					startTimeToDisplay = startTimeToDisplay.replace("AM", "am").replace("PM", "pm").replace(".00", "");

					String endDateToDisplay = endDate.format(formatter);
					String endTimeToDisplay = endTime.format(formatTime);
					endDateToDisplay = endDateToDisplay.replaceAll("\\[", "").replaceAll("\\]","");
					endTimeToDisplay = endTimeToDisplay.replaceAll("\\[", "").replaceAll("\\]", "");
					endTimeToDisplay = endTimeToDisplay.replace("AM", "am").replace("PM", "pm").replace(".00", "");
					
					dateToDisplay = "<p align=\"left\">"+startDateToDisplay+" - "+endDateToDisplay+"</p>";
					timeToDisplay = "<p align=\"left\"><font color=\"#1F3D7A\">"+startTimeToDisplay+" - "+endTimeToDisplay+"</font></p>";
						}
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FFFFFF", "!", 2);
						appendTasks("#01A9DB", dateToDisplay+timeToDisplay, 3);
						appendTasks("#0A1B2A", tasks, 4);
					
				}
		}
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
					+ textColour + "\"><b>" + s + "</b></font></td>");
		} else if (row == 4) {
			output.append("<td valign=\"top\" width=\"420px\"><font face=\"Eras Demi ITC\" size=\"3.5\" color=\""
					+ textColour + "\"><p align=\"left\">" + s + "</p></font></td></tr>");
		}
		else if (row == 5) {
			output.append("<td valign=\"top\" colspan=\"420px\" width=\"420px\"><font face=\"Eras Demi ITC\" size=\"3.5\" color=\""
					+ textColour + "\"><p align=\"left\">" + s + "</p></font></td></tr>");
		}

	}

	@Override
	public String show() throws BadLocationException {
		output = new StringBuilder();
		Display display = Display.getInstance();
		//page = display.getPaging();
		output.append("<html>");
			output.append("<table STYLE=\"margin-bottom: 15px;\" cellpadding=\"7px\" cellspacing=\"0px\" width=\"100%\">");
			output.append("<tr STYLE=\"margin-bottom: 5px;\" width=\"100px\" bgcolor=\"#084B8A\"><td height =\"30px\" width=\"100px\"colspan=\"4\"><font face=\"Tempus Sans ITC\" size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Completed</b></p></font></td></tr>");
			getCompletedTasks();
			output.append("</table>");
		
		output.append("</html>");

		return output.toString();


	}
	
	public ArrayList<Task> getCompletedList(){
		return data.getCompleted();
	}
	
}
