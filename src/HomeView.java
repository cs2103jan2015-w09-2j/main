//@author A0112715
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class HomeView implements View {
	private UserInterface UI;
	private JTextPane showToUser;
	private StyledDocument doc;
	private Style style;
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
	private DateTimeFormatter todayFormatter = DateTimeFormatter
			.ofPattern("EEE, dd MMMM");
	private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h.mma",
			Locale.US);
	private String todayDate = LocalDate.now().format(todayFormatter);
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
			if (i < 12) {
				i++;
				getTaskInfo(task);
				String t = "";
				String taskNo = "" + i + ".   ";
				if (task.isDeadlineTask()) {
					String tasks = taskDes;
					t = endTime.format(formatTime).replace("AM", "am")
							.replace("PM", "pm").replace(".00", "");
					t = t.toString().replaceAll("\\[", "")
							.replaceAll("\\]", " -");
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

				} else {
					String tasks = taskDes;
					t = startTime.format(formatTime).replace("AM", "am")
							.replace("PM", "pm").replace(".00", "")
							+ " - "
							+ endTime.format(formatTime).replace("AM", "am")
									.replace("PM", "pm").replace(".00", "");
					t = t.toString().replaceAll("\\[", "")
							.replaceAll("\\]", " -");

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
			}
		}
	}

	protected void getUpcoming() throws BadLocationException {

		for (Task task : upcoming) {
			if (i < 9) {
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
						appendTasks("#4B088A", tasks, 3);
						appendTasks("#01A9DB", t, 4);
					}
				}
			}
		}
	}

	protected void getSomeday() throws BadLocationException {

		for (Task task : someday) {
			if (i < 7) {
				String tasks = "";
				i++;
				String taskNo = "     " + i + ".   ";
				String t = task.toString().replaceAll("-", "to");
				t = task.toString().replaceAll("\\[", "")
						.replaceAll("\\]", " -");
				tasks = t + "\n";
				appendTasks("#848484", taskNo, 1);
				appendTasks("#FFFFFF", "&nbsp ", 2);
				appendTasks("#4B088A", tasks, 3);
				appendTasks("#FFFFFF", "&nbsp ", 4);
			}
		}
	}

//	protected void isTaskOverdue(Task task) {
//		isOverdue = false;
//		LocalDateTime now = LocalDateTime.now();
//		LocalDateTime endDateTime = task.getEnd();
//
//		if (endDateTime.isBefore(now)) {
//			isOverdue = true;
//		}
//	}

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
					+ textColour + "\"><p align=\"left\"><b>" + s + "</b></p></font></td>");
		} else if (row == 4) {
			output.append("<td width=\"420px\"><font size=\"5\" color=\""
					+ textColour + "\"><p align=\"left\">" + s + "</p></font></td></tr>");
		}

		// StyleConstants.setBold(style, isBold);
		// StyleConstants.setFontSize(style, 14);
		// StyleConstants.setBackground(style, bg);
		// StyleConstants.setForeground(style, c);
		// StyledDocument doc = showToUser.getStyledDocument();
		// SimpleAttributeSet center = new SimpleAttributeSet();
		// StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
		// doc.setParagraphAttributes(0, doc.getLength(), center, false);
		// doc.insertString(doc.getLength(), s, style);

	}

	public void show() throws BadLocationException {
		UI = UserInterface.getInstance();
		showToUser = UI.getShowToUser();
		style = showToUser.addStyle("Style", null);

		showToUser.setContentType("text/html");

		output.append("<html>");
		output.append("&nbsp");
		output.append("<table cellspacing=\"2px\" cellpadding=\"2px\" width=\"100%\">");
		output.append("<tr width=\"100px\" bgcolor=\"#084B8A\"><td  height =\"30px\" width=\"100px\"colspan=\"4\"><font size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Today ("+todayDate+") </b></p></font></td></tr>");
		getToday();
		output.append("&nbsp");
		output.append("</table>");

		if (i < 9) {
			output.append("<table cellspacing=\"2px\" cellpadding=\"3.5px\" width=\"100%\">");
			output.append("<tr width=\"100px\" bgcolor=\"#084B8A\"><td height =\"30px\" width=\"100px\"colspan=\"4\"><font size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Upcoming </b></p></font></td></tr>");
			getUpcoming();
			output.append("&nbsp");
			output.append("</table>");
		}

		if (i < 7) {
			output.append("<table cellspacing=\"2px\" cellpadding=\"3.5px\" width=\"100%\">");
			output.append("<tr width=\"100px\" bgcolor=\"#084B8A\"><td height =\"30px\" width=\"100px\"colspan=\"4\"><font size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Someday </b></p></font></td></tr>");
			getSomeday();
			output.append("&nbsp");
			output.append("</table>");
		}
		output.append("</html>");

		showToUser.setText(output.toString());

		/*
		 * StyleConstants.setFontSize(style, 7);
		 * StyleConstants.setForeground(style, Color.WHITE);
		 * StyleConstants.setBold(style, true);
		 * StyleConstants.setBackground(style, new Color(84, 121, 163));
		 * doc.insertString( doc.getLength(),
		 * "\n			    Today                         	                      \n",
		 * style); doc.insertString(doc.getLength(), "\n", style);
		 * StyleConstants.setBackground(style, Color.WHITE); getToday();
		 * 
		 * StyleConstants.setBold(style, true);
		 * StyleConstants.setFontSize(style, 15);
		 * StyleConstants.setForeground(style, Color.WHITE);
		 * doc.insertString(doc.getLength(), "\n", style);
		 * StyleConstants.setBackground(style, new Color(84, 121, 163));
		 * doc.insertString(doc.getLength(),
		 * "			 Upcoming                                   	        \n", style);
		 * StyleConstants.setForeground(style, Color.BLACK);
		 * StyleConstants.setBackground(style, Color.WHITE);
		 * doc.insertString(doc.getLength(), "\n", style); getUpcoming();
		 * 
		 * StyleConstants.setFontSize(style, 15); StyleConstants.setBold(style,
		 * true); StyleConstants.setForeground(style, Color.WHITE);
		 * doc.insertString(doc.getLength(), "\n", style);
		 * StyleConstants.setBackground(style, new Color(84, 121, 163));
		 * doc.insertString(doc.getLength(),
		 * "			  Someday                                   	        \n", style);
		 * StyleConstants.setForeground(style, Color.BLACK);
		 * StyleConstants.setBackground(style, Color.WHITE);
		 * doc.insertString(doc.getLength(), "\n", style); getSomeday();
		 * StyleConstants.setForeground(style, Color.BLUE.brighter());
		 */

	}

	public Task getTask(int numbering) throws IndexOutOfBoundsException {
		int index = numbering - 1;

		int todaySize = today.size();
		int dateSize = todaySize + upcoming.size();
		int allSize = dateSize + someday.size();

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

}
