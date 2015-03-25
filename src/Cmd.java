import java.util.ArrayList;


public abstract class Cmd {
	
	protected static final String MESSAGE_ADD = "Added to %1$s: “%2$s”";
	protected static final String MESSAGE_EDIT = "Edited";
	protected static final String MESSAGE_DELETE = "Deleted from %1$s: “%2$s”";
//	protected static final String MESSAGE_CLEAR = "all content deleted from %1$s";
//	protected static final String MESSAGE_EMPTY = "%1$s is empty";
	protected static final String MESSAGE_EMPTY_SEARCH = "Keyword “%1$s” is not found";
	protected static final String MESSAGE_INVALID = "Command is invalid.Please enter a valid command!";
//	protected static final String MESSAGE_NO_VALUE = "Please indicate a task to %1$s";
	protected static final String MESSAGE_NO_FILE = "File not available";

	
	public abstract boolean execute();
	
	protected void writeToFile(){
		Data myList = Data.getInstance();
		Storage file = new Storage();
		
		ArrayList<Task> tasks = myList.getData();
		assert tasks != null;
		file.writeToFile(tasks);
	}

}
