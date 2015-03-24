import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class TodayView implements View{

	ArrayList<Task> today;
	
	public TodayView(){
		update();
	}

	@Override
	public Task getTask(int index) {
		index--;
		return today.get(index);
	}

	@Override
	public void update() {
		Data data = Data.getInstance();
		
		this.today = data.getToday();
		
	}
	
	
	protected String getTask() {
		String tasks = "";
		int i =0;
		for (Task task : today) {
			i++;
			tasks += "  "+i + ". " + task.toString() + "\n";
		}
		return tasks;
	}

	@Override
	public void show() throws BadLocationException {
		// TODO Auto-generated method stub
		UserInterface UI = UserInterface.getInstance();
		JTextPane showToUser = UI.getShowToUser();
		
		StyledDocument doc = showToUser.getStyledDocument();
		Style style = showToUser.addStyle("Style", null);
		StyleConstants.setForeground(style, Color.BLUE.brighter());
		doc.insertString(doc.getLength(), " Today: \n", style);
		
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), getTask(), style);
	}

}
