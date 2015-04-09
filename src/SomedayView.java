//@author A0112715R
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class SomedayView extends SingleView implements View {
	private StringBuilder output = new StringBuilder();

	@Override
	public void update() {
		Data data = Data.getInstance();

		setList(data.getSomeday());

	}

	protected void getSomeday() throws BadLocationException {
		int i = 0;
		for (Task task : getList()) {
			String tasks = "";
			i++;
			String taskNo = "     " + i + ".   ";
			String t = task.toString().replaceAll("-", "to");
			t = task.toString().replaceAll("\\[", "")
					.replaceAll("\\]", " -");
			tasks = t + "\n";
			if (task.getIsCompleted()) { //completed tasks are green and striked thru
				appendTasks("#848484", taskNo, 1);
				appendTasks("#FFFFFF", "!", 2);
				appendTasks("#848484", "<strike>"+tasks+"</strike>", 5);
			} else{
			appendTasks("#848484", taskNo, 1);
			appendTasks("#FFFFFF", "!", 2);
			appendTasks("#0A1B2A", tasks, 5);
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
		output.append("<tr STYLE=\"margin-bottom: 5px;\" width=\"100px\" bgcolor=\"#084B8A\"><td height =\"30px\" width=\"100px\"colspan=\"4\"><font face=\"Tempus Sans ITC\" size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Someday </b></p></font></td></tr>");
		getSomeday();
		output.append("</table>");

		output.append("</html>");

		return output.toString();
	}

}
