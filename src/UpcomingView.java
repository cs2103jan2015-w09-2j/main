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
	private DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("dd-MM-yyyy");

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
		int i =0;
		for (Task task : getList()) {
			i++;
			getTaskInfo(task);
			isTaskOverdue(task);
			String t = "";
			String numbering = "     " + i + ".   ";
			if (task.isDeadlineTask()) {
				String tasks = taskDes;
				t = " (by " + endDate.format(formatter) + ")";
				t = t.replaceAll("\\[", "").replaceAll("\\]", " -");
				if (isOverdue) {
//					appendTasks(Color.GRAY, Color.WHITE, false, numbering);
//					appendTasks(Color.RED, Color.WHITE, true, "! ");
//					appendTasks(Color.BLUE.darker(), Color.WHITE, false,
//							tasks);
//					appendTasks(Color.BLUE.darker(), Color.WHITE, false, t
//							+ "\n");
				} else {
					appendTasks(Color.GRAY, Color.white, false, numbering);
					appendTasks(Color.BLUE.darker(), Color.white, false, tasks);
					appendTasks(Color.CYAN.darker(), Color.white, false, t
							+ "\n");
				}
			}

			else {
				String tasks = taskDes;

				t = "  (starts on " + startDate.format(formatter) + ")";
				t = t.replaceAll("\\[", "").replaceAll("\\]", " -");
				if (isOverdue) {
					appendTasks(Color.GRAY, Color.WHITE, false, numbering);
					appendTasks(Color.RED, Color.WHITE, true, "! ");
					appendTasks(Color.BLUE.darker(), Color.WHITE, false,
							tasks);
					appendTasks(Color.BLUE.darker(), Color.WHITE, false, t
							+ "\n");
				} else {
					appendTasks(Color.GRAY, Color.white, false, numbering);
					appendTasks(Color.BLUE.darker(), Color.white, false, tasks);
					appendTasks(Color.CYAN.darker(), Color.white, false, t
							+ "\n");
				}
			}
		}
	}

	public void appendTasks(Color c, Color bg, boolean isBold, String s)
			throws BadLocationException {
		StyleConstants.setBold(style, isBold);
		StyleConstants.setFontSize(style, 14);
		StyleConstants.setBackground(style, bg);
		StyleConstants.setForeground(style, c);
		doc.insertString(doc.getLength(), s, style);
	}

	@Override
	public void show() throws BadLocationException {

		StyleConstants.setBold(style, true);
//		StyleConstants.setFontSize(style, 4);
//		StyleConstants.setBackground(style, new Color(84, 121, 163));
//		doc.insertString(doc.getLength(),
//				"\n\n\n							                                        ", style);
		StyleConstants.setFontSize(style, 15);
		StyleConstants.setForeground(style, Color.WHITE);
		doc.insertString(doc.getLength(),"\n", style); 
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		doc.insertString(doc.getLength(),
					"			   Upcoming                                 	        \n", style); 
//		StyleConstants.setFontSize(style, 4);
//		StyleConstants.setBackground(style, new Color(84, 121, 163));
//		doc.insertString(doc.getLength(),
//				"							                                        \n\n\n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		StyleConstants.setBackground(style, Color.WHITE);
		doc.insertString(doc.getLength(),"\n", style); 
		getUpcoming();
	}

}
