import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class HelpView extends SingleView implements View {
	private int i;
	private UserInterface UI = UserInterface.getInstance();
	private JTextPane showToUser = UI.getShowToUser();
	private StyledDocument doc = showToUser.getStyledDocument();
	private Style style = showToUser.addStyle("Style", null);
	private StringBuilder output = new StringBuilder();

	private String helpToAdd = "<p>&nbsp&nbsp 1. Add task by end date/time</p>"
			+ "<p>&nbsp&nbsp 2. Add task</p>"
			+ "<p>&nbsp&nbsp 3. Add task from start date/time</p>"
			+ "<p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp to end date/time</p>";

	private String helpToEdit = "<p>1. &nbsp Edit taskno desc </p>"
			+ "<p>2. &nbsp Edit taskno desc by date </p>"
			+ "<p>3. &nbsp Edit taskno by date </p>"
			+ "<p>4. &nbsp Edit taskno desc from date to date </p>"
			+ "<p>5. &nbsp Edit taskno from date to date </p>"
			+ "<p>6. &nbsp Edit taskno someday </p>"
			+ "<p>7. &nbsp Edit taskno desc from date </p>"
			+ "<p>8. &nbsp Edit taskno from date</p>"
			+ "<p>9. &nbsp Edit taskno desc to date </p>"
			+ "<p>10. Edit taskno to date </p>"
			+ "<p>11. Edit taskno desc someday</p>";

	private String helpToDelete = "<p>1. &nbsp Delete taskno </p>";
	private String helpToMarkComplete = "<p>1. &nbsp Done taskno </p>";
	private String helpToSearch = "<p>1. &nbsp Search keyword </p>";
	private String helpToDiffViews = "<p>Today, Someday, Upcoming, Home </p>";

	@Override
	public void update() {
	}

	@Override
	public void show() throws BadLocationException {

		UI = UserInterface.getInstance();
		showToUser = UI.getShowToUser();
		style = showToUser.addStyle("Style", null);

		showToUser.setContentType("text/html");

		output.append("<html>");
		output.append("&nbsp");
		output.append("<table border=\"\" cellspacing=\"2px\" cellpadding=\"3px\" width=\"100%\">");
		output.append("<tr width=\"100px\" bgcolor=\"#5C5E5C\"><td height =\"10px\" width=\"100px\"colspan=\"4\"><font size=\"5\" color=\"#FFFFFF\"><p align=\"center\"><b>Help is always here! </b></p></font></td></tr>");
		output.append("<tr width=\"100%\"><td bgcolor=\"#CC00CC\" width=\"50%\"><font size=\"4\" color=\"#FFFFFF\"><p align=\"center\">So you wanna <b>ADD</b> tasks?</p></font></td><td bgcolor=\"#6B006B\" width=\"50%\"><font size=\"4\" color=\"#FFFFFF\"><p align=\"center\">So you wanna <b>EDIT</b> tasks?</p></td></font></tr>");
		;
		output.append("<tr><td bgcolor=\"#FFCCFF\" valign=\"top\">" + helpToAdd
				+ "</td><td bgcolor=\"#EDE0ED\">" + helpToEdit + "</td></tr>");
		output.append("<tr width=\"100%\"><td bgcolor=\"#B82E00\" width=\"50%\"><font size=\"4\" color=\"#FFFFFF\"><p align=\"center\">So you wanna <b>DELETE</b> tasks?</p></font></td><td bgcolor=\"#CCCC00\" width=\"50%\"><font size=\"4\" color=\"#FFFFFF\"><p align=\"center\">So you wanna mark tasks as <b>DONE</b>?</p></font></td></tr>");
		;
		output.append("<tr><td bgcolor=\"#FFC266\" valign=\"top\">" + helpToDelete + "</td><td bgcolor=\"#FFFF85\">"
				+ helpToMarkComplete + "</td></tr>");
		output.append("<tr width=\"100%\"><td bgcolor=\"#008100\" width=\"50%\"><font size=\"4\" color=\"#FFFFFF\"><p align=\"center\">So you wanna <b>SEARCH</b> for tasks?</p></font></td><td bgcolor=\"#009999\" width=\"50%\"><font size=\"4\" color=\"#FFFFFF\"><p align=\"center\">So you wanna change <b>VIEW?</b></p></font></td></tr>");
		;
		output.append("<tr><td bgcolor=\"#E0FFA3\" valign=\"top\">" + helpToSearch + "</td><td bgcolor=\"#B2F0E0\">"
				+ helpToDiffViews + "</td></tr>");
		output.append("</table>");
		output.append("</html>");

		showToUser.setText(output.toString());

	}

}
