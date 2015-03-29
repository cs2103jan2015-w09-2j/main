import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class CompletedView extends SingleView implements View{
	private int i;
	private UserInterface UI = UserInterface.getInstance();
	private JTextPane showToUser = UI.getShowToUser();
	private StyledDocument doc = showToUser.getStyledDocument();
	private Style style = showToUser.addStyle("Style", null);
	
	@Override
	public void update() {
		Data data = Data.getInstance();
		
		setList(data.getCompleted());
	}
	
	protected void getCompletedTask() throws BadLocationException {
		 int i =0;
		 for (Task task : getList()) {
			 String tasks = "";
		 i++;
		 String numbering = "  "+i+".  ";
		 appendTasks(Color.GRAY.brighter(), numbering);
		 tasks =task.getDescription() + "\n";
		 appendTasks(Color.GREEN.darker(),tasks);
		 }
	}
	
	public void appendTasks(Color c, String s) throws BadLocationException {
		StyleConstants.setBold(style, false);
		StyleConstants.setFontSize(style, 16);
		StyleConstants.setBackground(style, Color.white);
		StyleConstants.setForeground(style, c);
		doc.insertString(doc.getLength(), s, style);
	}

	@Override
	public void show() throws BadLocationException {
		UserInterface UI = UserInterface.getInstance();
		JTextPane showToUser = UI.getShowToUser();
	//	String COMPLETED_TASKS_MESSAGE = "Amazing! You have completed " + i + " tasks";
		StyledDocument doc = showToUser.getStyledDocument();
		Style style = showToUser.addStyle("Style", null);
	//	StyleConstants.setBold(style, true);
	//	StyleConstants.setFontSize(style, 18);
	//	StyleConstants.setForeground(style, Color.BLUE);
	//	doc.insertString(doc.getLength(), "\n  	 " +COMPLETED_TASKS_MESSAGE+"         \n", style);
		StyleConstants.setForeground(style, Color.WHITE);
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		StyleConstants.setBold(style, true);
		StyleConstants.setFontSize(style, 18);
		doc.insertString(doc.getLength(), "\n  			  Completed  			       \n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		StyleConstants.setBackground(style, Color.WHITE);
		StyleConstants.setBold(style, false);
		StyleConstants.setFontSize(style, 16);
		
		getCompletedTask();
		
	}

}
