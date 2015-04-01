//@author A0112715
import java.awt.Color;
import java.time.LocalDateTime;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class SearchView extends SingleView implements View{
	private UserInterface UI = UserInterface.getInstance();
	private JTextPane showToUser = UI.getShowToUser();
	private StyledDocument doc = showToUser.getStyledDocument();
	private Style style = showToUser.addStyle("Style", null);
	private boolean isOverdue;
	
	public SearchView(String searchedText){		
		setList(data.getSearched(searchedText));
	}
	

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
		
	protected void getSearchResults() throws BadLocationException {
		 int i =0;
		 for (Task task : getList()) {
		 String tasks = "";
		 i++;
		 //isTaskOverdue(task);
		 isOverdue = task.isOverdue();
		 String numbering = "    "+i+". ";
		 tasks =task.getDescription() + "\n";
		if(isOverdue){
				appendTasks(Color.GRAY,Color.WHITE, false,numbering);
				appendTasks(Color.RED, Color.WHITE, true, " ! ");
				appendTasks(Color.BLUE.darker(),Color.WHITE,false, tasks);
			}
			else{
				appendTasks(Color.GRAY,Color.WHITE, false,numbering);
				appendTasks(Color.BLUE.darker(),Color.WHITE,false, tasks);
			}
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
	
	protected void isTaskOverdue(Task task) {
		isOverdue = false;
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime endDateTime = task.getEnd();
		
		if (endDateTime.isBefore(now)) {
			isOverdue = true;
		}
	}

	@Override
	public void show() throws BadLocationException {
		UserInterface UI = UserInterface.getInstance();
		JTextPane showToUser = UI.getShowToUser();
		
		StyledDocument doc = showToUser.getStyledDocument();
		Style style = showToUser.addStyle("Style", null);
		StyleConstants.setForeground(style, Color.WHITE);
		StyleConstants.setBackground(style, new Color(84, 121, 163));
		StyleConstants.setBold(style, true);
		StyleConstants.setFontSize(style, 15);
		doc.insertString(doc.getLength(), "\n  		             Search Results:                                          \n", style);
		
		//StyleConstants.setForeground(style, Color.BLACK);
	    getSearchResults();
		
	}

}
