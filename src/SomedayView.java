//@author A0112715
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class SomedayView extends SingleView implements View {
	private UserInterface UI = UserInterface.getInstance();
	private JTextPane showToUser = UI.getShowToUser();
	private StyledDocument doc = showToUser.getStyledDocument();
	private Style style = showToUser.addStyle("Style", null);

	private StringBuilder output = new StringBuilder();
	
	@Override
	public void update() {
		Data data = Data.getInstance();

		setList(data.getSomeday());

	}

	protected void getSomeday() throws BadLocationException {
		int i = 0;
		for (Task task : getList()) {
			if (i < 10) {
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
			output.append("<td width=\"120px\"><font size=\"5\" color=\""
					+ textColour + "\"><p align=\"left\">" + s + "</p></font></td>");
		} else if (row == 4) {
			output.append("<td width=\"420px\"><font size=\"5\" color=\""
					+ textColour + "\"><p align=\"left\">" + s + "</p></font></td></tr>");
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
			output.append("<tr width=\"100px\" bgcolor=\"#084B8A\"><td height =\"30px\" width=\"100px\"colspan=\"4\"><font size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Someday </b></p></font></td></tr>");
			getSomeday();
			output.append("&nbsp");
			output.append("</table>");
		output.append("</html>");

		showToUser.setText(output.toString());
	}

}
