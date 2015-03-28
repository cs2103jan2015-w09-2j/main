import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class SomedayView extends SingleView implements View{

	@Override
	public void update() {
		Data data = Data.getInstance();
		
		setList(data.getSomeday());
		
	}
	
	protected String getSomedayTask() {
		String tasks = "";
		int i =0;
		for (Task task : getList()) {
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
