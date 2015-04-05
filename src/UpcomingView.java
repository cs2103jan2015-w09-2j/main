//@author A0112715
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class UpcomingView extends SingleView implements View {

	private UserInterface UI = UserInterface.getInstance();
	private JTextPane showToUser = UI.getShowToUser();
	private StyledDocument doc = showToUser.getStyledDocument();
	private Style style = showToUser.addStyle("Style", null);
	private String taskDes;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private boolean isOverdue;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
			"EEE, dd MMM yyyy", Locale.US);
	private StringBuilder output = new StringBuilder();

	@Override
	public void update() {
		Data data = Data.getInstance();

		setList(data.getUpcoming());
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

	protected void isTaskOverdue(Task task) {
		isOverdue = false;
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime endDateTime = task.getEnd();

		if (endDateTime.isBefore(now)) {
			isOverdue = true;
		}
	}

	protected void getUpcoming() throws BadLocationException {
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
						appendTasks("#4B088A", tasks, 4);

					} else {
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FFFFFF", "!", 2);
						appendTasks("#01A9DB", t, 3);
						appendTasks("#4B088A", tasks, 4);
					}
				}

				else {
					String tasks = taskDes;

					t = startDate.format(formatter);
					t = t.replaceAll("\\[", "").replaceAll("\\]", "-");
					if (task.isOverdue()) {
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FF0000", "!", 2);
						appendTasks("#4B088A", tasks, 3);
						appendTasks("#01A9DB", t, 4);
					} else {
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FFFFFF", "!", 2);
						appendTasks("#01A9DB", t, 3);
						appendTasks("#4B088A", tasks, 4);
					}
				}
			}
		}
	}

	public void appendTasks(String textColour, String s, int row)
			throws BadLocationException {
		if (row == 1) {
			output.append("<tr width=\"100px\" >"
					+ "<td width=\"40px\"><font size=\"4\" color=\""
					+ textColour + "\"><p align=\"right\"><b>" + s
					+ "</b></p></font></td>");
		} else if (row == 2) {
			// output.append("<td width=\"1px\"><img src=\"alert.jpg\"></td>");
			output.append("<td width=\"1px\"><font size=\"5\" color=\""
					+ textColour + "\"><p align=\"center\"><b>" + s
					+ "</b></p></font></td>");
		} else if (row == 3) {
			output.append("<td width=\"150px\"><font size=\"4\" color=\""
					+ textColour + "\"><p align=\"left\"><b>" + s
					+ "</b></p></font></td>");
		} else if (row == 4) {
			output.append("<td width=\"420px\"><font size=\"5\" color=\""
					+ textColour + "\"><p align=\"left\">" + s
					+ "</p></font></td></tr>");
		}

	}

	@Override
	public void show() throws BadLocationException {
		UI = UserInterface.getInstance();
		showToUser = UI.getShowToUser();
		style = showToUser.addStyle("Style", null);

		showToUser.setContentType("text/html");

		output.append("<html>");
		output.append("&nbsp");
		output.append("<table cellspacing=\"2px\" cellpadding=\"3.5px\" width=\"100%\">");
		output.append("<tr width=\"100px\" bgcolor=\"#084B8A\"><td height =\"30px\" width=\"100px\"colspan=\"4\"><font size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Upcoming </b></p></font></td></tr>");
		getUpcoming();
		output.append("&nbsp");
		output.append("</table>");
		output.append("</html>");

		showToUser.setText(output.toString());

	}

}
