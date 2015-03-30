//@author A0112715
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
		int i=0;
		for (Task task : getList()) {
			String tasks = "";
			i++;
			String numbering = "     " + i + ".   ";
			appendTasks(Color.GRAY.brighter(), Color.WHITE, false, numbering);
			String t = task.toString().replaceAll("-", "to");
			t = task.toString().replaceAll("\\[", "").replaceAll("\\]", " -");
			tasks = t + "\n";
			appendTasks(Color.BLUE.darker(), Color.WHITE,false, tasks);
		}

	}


	public void appendTasks(Color c, Color bg, boolean isBold, String s)
			throws BadLocationException {
		StyleConstants.setBold(style, isBold);
		StyleConstants.setFontSize(style, 14);
		StyleConstants.setBackground(style, bg);
		StyleConstants.setForeground(style, c);
		doc.insertString(doc.getLength(), s, style);
		
	}
	
	@Override
	public void show() throws BadLocationException {
		// TODO Auto-generated method stub

//		StyleConstants.setBold(style, true);
//		StyleConstants.setFontSize(style, 4);
//		StyleConstants.setBackground(style, new Color(84, 121, 163));
//		doc.insertString(doc.getLength(),
//				"\n\n\n							                                        ", style);
		StyleConstants.setFontSize(style, 15);
		StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.WHITE);
		doc.insertString(doc.getLength(),"\n", style); 
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		doc.insertString(doc.getLength(),
					"			   Someday                                 	        \n", style); 
//		StyleConstants.setFontSize(style, 4);
//		StyleConstants.setBackground(style, new Color(84, 121, 163));
//		doc.insertString(doc.getLength(),
//				"							                                        \n\n\n", style);
		StyleConstants.setForeground(style, Color.BLACK);
		StyleConstants.setBackground(style, Color.WHITE);
		doc.insertString(doc.getLength(),"\n", style); 
		getSomeday();
		StyleConstants.setForeground(style, Color.BLUE.brighter());
	}

}
