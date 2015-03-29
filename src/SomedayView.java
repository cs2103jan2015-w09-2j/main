import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class SomedayView extends SingleView implements View{
	private UserInterface UI = UserInterface.getInstance();
	private JTextPane showToUser = UI.getShowToUser();
	private StyledDocument doc = showToUser.getStyledDocument();
	private Style style = showToUser.addStyle("Style", null);
	
	@Override
	public void update() {
		Data data = Data.getInstance();
		
		setList(data.getSomeday());
		
	}
	
	protected void getSomeday() throws BadLocationException {
		 int i =0;
		 for (Task task : getList()) {
			 String tasks = "";
		 i++;
		 String numbering = "  "+i+".  ";
		 appendTasks(Color.GRAY.brighter(), numbering);
		 tasks =task.getDescription() + "\n";
		 appendTasks(Color.BLUE.darker(),tasks);
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
		// TODO Auto-generated method stub
		StyleConstants.setForeground(style, Color.WHITE);
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		doc.insertString(doc.getLength(), "\n  			   Someday   			        \n", style);
		getSomeday();
	}

}
