import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class UpcomingView implements View{

	ArrayList<Task> upcoming;
	
	public UpcomingView(){
		update();
	}
	
	@Override
	public Task getTask(int index) {
		index--;
		return upcoming.get(index);
	}

	@Override
	public void update() {
		Data data = Data.getInstance();
		
		upcoming = data.getUpcoming();
	}
	
	protected String getTask() {
		String tasks = "";
		int i =0;
		for (Task task : upcoming) {
			i++;
			String t =	task.toString().replaceAll("\\[", "").replaceAll("\\]"," -");
			tasks += "  		      "+i + ".     " + t + "\n";
			}
		return tasks;
	}

	@Override
	public void show() throws BadLocationException {
		UserInterface UI = UserInterface.getInstance();
		JTextPane showToUser = UI.getShowToUser();
		
		StyledDocument doc = showToUser.getStyledDocument();
		Style style = showToUser.addStyle("Style", null);
		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), "\n  			   Upcoming:\n", style);
		doc.insertString(doc.getLength(), " ----------------------------------------------------------------------------------------------------------- \n", style);
		
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), getTask(), style);
		
	}

}
