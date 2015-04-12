import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.BadLocationException;

//@author A0111867A
public interface View {
		
	void update();
	
	public String show() throws BadLocationException, IOException;
	
	public Task getTask(int index) throws IndexOutOfBoundsException;
	
	public ArrayList<Task> getList();
}
