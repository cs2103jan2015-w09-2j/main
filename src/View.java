import javax.swing.text.BadLocationException;


public interface View {
		
	void update();
	
	void show() throws BadLocationException;
	
	Task getTask(int index);
	
}
