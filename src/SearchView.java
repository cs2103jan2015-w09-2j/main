import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class SearchView extends SingleView implements View{

	public SearchView(String searchedText){		
		setList(data.getSearched(searchedText));
	}
	

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
		
	protected String getTask() {
		String tasks = "";
		int i =0;
		for (Task task : getList()) {
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
		doc.insertString(doc.getLength(), " Search Results: \n", style);
		
		StyleConstants.setForeground(style, Color.BLACK);
		doc.insertString(doc.getLength(), getTask(), style);
		
	}

}
