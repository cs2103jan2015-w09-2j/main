import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.BadLocationException;


public interface View {
		
	void update();
	
	void show() throws BadLocationException, IOException;
	
	Task getTask(int index) throws IndexOutOfBoundsException;
	
	ArrayList<Task> getList();
}
