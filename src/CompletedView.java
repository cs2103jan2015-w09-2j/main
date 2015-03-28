import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class CompletedView implements View{

	ArrayList<Task> completed;
	
	public CompletedView(){
		update();
	}
	
	@Override
	public Task getTask(int index) {
		index--;
		int size = completed.size();
		
		if(index > -1 && index < size){
			return completed.get(index);
		}
		else{
			return null;
		}
	}

	@Override
	public void update() {
		Data data = Data.getInstance();
		
		completed = data.getCompleted();
	}
	
	protected String getTask() {
		String tasks = "";
		int i =0;
		for (Task task : completed) {
			i++;
			tasks += "  "+i + ". " + task.toString() + "\n";
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
		doc.insertString(doc.getLength(), " Completed Tasks: \n", style);
		
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), getTask(), style);
		
	}

}
