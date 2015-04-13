import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.BadLocationException;

//@author A0111867A
public interface View {
		
	/**
	 * Call to get update from data
	 */
	public void update();
	
	/**
	 * Returns the html intepretation of data
	 */
	public String show() throws BadLocationException, IOException;
	
	/**
	 * Returns the task specified by index
	 * 
	 * @param index reference of the task
	 */
	public Task getTask(int index) throws IndexOutOfBoundsException;
	
	/**
	 * Returns the arraylist of task representation of this class
	 */
	public ArrayList<Task> getList();
}
