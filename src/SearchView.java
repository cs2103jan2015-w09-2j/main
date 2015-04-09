//@author A0112715R
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class SearchView extends SingleView implements View {
	private UserInterface UI = UserInterface.getInstance();

	private String searchedText = "";
	private String taskDes;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private boolean isOverdue;
	private DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("dd-MM-yyyy");
	private StringBuilder output = new StringBuilder();

	public SearchView(String searchedText) {
		System.out.println("search:" + searchedText);
		this.searchedText = searchedText;
		update();
	}

	@Override
	public void update() {
		setList(data.getSearched(searchedText));
	}

	protected void isTaskOverdue(Task task) {
		isOverdue = false;
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime endDateTime = task.getEnd();

		if (endDateTime.isBefore(now)) {
			isOverdue = true;
		}
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

	protected void getSearchResults() throws BadLocationException {
		int i = 0;
		for (Task task : getList()) {
			if (i < 10) {
				i++;
				getTaskInfo(task);
				String t = "";
				String taskNo = "     " + i + ".   ";
				if (task.isDeadlineTask()) {
					String tasks = taskDes;
					t = endDate.format(formatter);
					t = t.replaceAll("\\[", "").replaceAll("\\]", "-");
					if (task.isOverdue()) {
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FF0000", "!", 2);
						appendTasks("#01A9DB", t, 3);
						appendTasks("#0A1B2A", tasks, 4);

					} else {
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FFFFFF", "!", 2);
						appendTasks("#01A9DB", t, 3);
						appendTasks("#0A1B2A", tasks, 4);
					}
				}

				else {
					String tasks = taskDes;
					if (!task.isFloatingTask() && !task.isDeadlineTask()) {
						t = startDate.format(formatter);
					}
					t = t.replaceAll("\\[", "").replaceAll("\\]", "-");
					if (task.isOverdue()) {
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FF0000", "!", 2);
						appendTasks("#01A9DB", t, 3);
						appendTasks("#0A1B2A", tasks, 4);
					} else {
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FFFFFF", "!", 2);
						appendTasks("#01A9DB", t, 3);
						appendTasks("#0A1B2A", tasks, 4);
					}
				}
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
					+ textColour + "\"><p align=\"left\"><b>" + s + "</b></p></font></td>");
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
		output.append("<html>");
		output.append("<table STYLE=\"margin-bottom: 15px;\" cellpadding=\"3px\" cellspacing=\"0px\" width=\"100%\">");
		output.append("<tr STYLE=\"margin-bottom: 5px;\" width=\"100px\" bgcolor=\"#084B8A\"><td height =\"30px\" width=\"100px\"colspan=\"4\"><font face=\"Tempus Sans ITC\" size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Search Results</b></p></font></td></tr>");
		getSearchResults();
		output.append("</table>");

		output.append("</html>");

		return output.toString();

	}

}
