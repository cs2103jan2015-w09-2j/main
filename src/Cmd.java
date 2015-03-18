import java.util.ArrayList;


public abstract class Cmd {
	
	protected static final String MESSAGE_ADD = "added to %1$s: “%2$s”";
	protected static final String MESSAGE_EDIT = "edit";
	protected static final String MESSAGE_DELETE = "deleted from %1$s: “%2$s”";
	protected static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	protected static final String MESSAGE_EMPTY = "%1$s is empty";
	protected static final String MESSAGE_EMPTY_SEARCH = "no text matches “%1$s”";
	protected static final String MESSAGE_INVALID = "command is invalid";
	protected static final String MESSAGE_NO_VALUE = "please indicate word to %1$s";
	protected static final String MESSAGE_NO_FILE = "file not available";
	
	public abstract Display execute(Data myList);
	
	protected void writeToFile(Data myList){
		Storage file = new Storage();
		ArrayList<Task> tasks = myList.getData();
		assert tasks != null;
		file.writeToFile(tasks);
	}

}