import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class CompletedView extends SingleView implements View {
	private int i;
	private StringBuilder output = new StringBuilder();
	private DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("dd-MM-yyyy");
	private String taskDes;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	
	@Override
	public void update() {
		Data data = Data.getInstance();

		setList(data.getCompleted());
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

	protected void getCompletedTask() throws BadLocationException {
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
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FFFFFF", "!", 2);
						appendTasks("#009900", t, 3);
						appendTasks("#E68A00", tasks, 4);
				}

				else {
					String tasks = taskDes;
					if(!task.isFloatingTask()){
					t = startDate.format(formatter);
					t = t.replaceAll("\\[", "").replaceAll("\\]", "-");
					}
						appendTasks("#848484", taskNo, 1);
						appendTasks("#FFFFFF", "!", 2);
						appendTasks("#009900", t, 3);
						appendTasks("#E68A00", tasks, 4);
					
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
			output.append("<td width=\"120px\"><font size=\"4\" color=\""
					+ textColour + "\"><p align=\"left\"><b>" + s
					+ "</b></p></font></td>");
		} else if (row == 4) {
			output.append("<td width=\"420px\"><font size=\"5\" color=\""
					+ textColour + "\"><p align=\"left\">" + s
					+ "</p></font></td></tr>");
		}

	}

	@Override
	public String show() throws BadLocationException {
		output = new StringBuilder();

		output.append("<html>");
		output.append("&nbsp");
		output.append("<table cellspacing=\"2px\" cellpadding=\"3.5px\" width=\"100%\">");
		output.append("<tr width=\"100px\" bgcolor=\"#084B8A\"><td height =\"30px\" width=\"100px\"colspan=\"4\"><font size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Completed </b></p></font></td></tr>");
		getCompletedTask();
		output.append("&nbsp");
		output.append("</table>");
		output.append("</html>");

		return output.toString();


	}

}
