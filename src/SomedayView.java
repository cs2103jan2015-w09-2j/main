//@author A0112715R
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.BadLocationException;

public class SomedayView extends SingleView implements View {
	private StringBuilder output;
	private int i = 0;
	private int page = 1;

	/**
	 * Updates the list of tasks 
	 */
	@Override
	public void update() {
		Data data = Data.getInstance();

		setList(data.getSomeday());

	}

	/**
	 * Supports paging. Sets the tasks to be shown for each "page"
	 * 
	 * @return List<Task>
	 */
	protected List<Task> getTasksForPage() {
		if (page == 0) {
			page = 1;
		}
		List<Task> tasksForPage = new ArrayList<Task>();
		int startTaskNo = 0;

		if (getList().size() >= 5) {
			startTaskNo = (page - 1) * 5;
			int endTaskNo = startTaskNo + 5;
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
		i = startTaskNo;
		return tasksForPage;
	}

	/**
	 * Gets list of someday task and formats them
	 */
	protected void getSomeday() throws BadLocationException {
		for (Task task : getTasksForPage()) {
			i++;
			String taskNo = "     " + i + ".   ";
			String tasks = task.toString() + "\n";
				appendTasks("#848484", taskNo, 1);
				appendTasks("#FFFFFF", "!", 2);
				appendTasks("#0A1B2A", tasks, 3);
		}
	}

	/**
	 * Appends to Stringbuilder output
	 * @param textColour,text,row
	 */
	public void appendTasks(String textColour, String text, int row)
			throws BadLocationException {
		if (row == 1) {
			output.append("<tr width=\"100px\" >" + "<td valign=\"top\""
					+ " width=\"40px\"><font size=\"4\" color=\"" + textColour
					+ "\"><p align=\"right\"><b>" + text + "</b></p></font></td>");
		} else if (row == 2) {
			// output.append("<td width=\"1px\"><img src=\"alert.jpg\"></td>");
			output.append("<td valign=\"top\" width=\"1px\"><font size=\"4.5\" color=\""
					+ textColour
					+ "\"><p align=\"center\"><b>"
					+ text
					+ "</b></p></font></td>");
		} else if (row == 3) {
			output.append("<td valign=\"top\" colspan=\"420px\" width=\"420px\"><font face=\"Eras Demi ITC\" size=\"4\" color=\""
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
	 */
	@Override
	public String show() throws BadLocationException {
		output = new StringBuilder();
		Display display = Display.getInstance();
		page = display.getPaging();

		output.append("<html>");

		output.append("<table STYLE=\"margin-bottom: 15px;\" cellpadding=\"3px\" cellspacing=\"0px\" width=\"100%\">");
		output.append("<tr STYLE=\"margin-bottom: 5px;\" width=\"100px\" bgcolor=\"#084B8A\"><td height =\"30px\" width=\"100px\"colspan=\"4\"><font face=\"Tempus Sans ITC\" size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Someday </b></p></font></td></tr>");
		getSomeday();
		output.append("</table>");

		output.append("</html>");

		return output.toString();
	}

	/**
	 * This is used for Testing
	 * 
	 * @return ArrayList<Task>
	 */
	public ArrayList<Task> getSomedayList() {
		ArrayList<Task> somedayList = data.getSomeday();
		return somedayList;
	}

}
