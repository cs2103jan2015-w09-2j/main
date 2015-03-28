import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class SomedayView implements View{

	ArrayList<Task> someday;
	
	public SomedayView(){
		update();
	}

	@Override
	public Task getTask(int index) {
		index--;
		int size = someday.size();
		
		if(index > -1 && index < size){
			return someday.get(index);
		}
		else{
			return null;
		}
	}

	@Override
	public void update() {
		Data data = Data.getInstance();
		
		this.someday = data.getSomeday();
		
	}
	
	protected String getSomedayTask() {
		String tasks = "";
		int i =0;
		for (Task task : someday) {
			i++;
			tasks += "  		      "+i + ".     " + task.toString() + "\n";
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
		doc.insertString(doc.getLength(), "\n  			   Someday: \n", style);
		doc.insertString(doc.getLength(), " ----------------------------------------------------------------------------------------------------------- \n", style);
		
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), getSomedayTask(), style);
	}

}
