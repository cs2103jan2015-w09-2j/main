

public abstract class Cmd {
	
	protected static final String MESSAGE_ADD = "Added to %1$s: “%2$s”";
	protected static final String MESSAGE_UNDO_ADD = "Undo add";
	protected static final String MESSAGE_EDIT = "Edited";
	protected static final String MESSAGE_UNDO_EDIT = "Undo Edited";
	protected static final String MESSAGE_DELETE = "Delete";
	protected static final String MESSAGE_UNDO_DELETE = "Undo Delete";
	protected static final String MESSAGE_COMPLETED = "Completed";
	protected static final String MESSAGE_UNDO_COMPLETED = "Undo Completed";
//	protected static final String MESSAGE_CLEAR = "all content deleted from %1$s";
//	protected static final String MESSAGE_EMPTY = "%1$s is empty";
	protected static final String MESSAGE_EMPTY_SEARCH = "Keyword “%1$s” is not found";
	protected static final String MESSAGE_UNDO = "Undo";
	protected static final String MESSAGE_UNDO_EMPTY = "nothing to undo";
	protected static final String MESSAGE_INVALID = "Command is invalid.Please enter a valid command!";
//	protected static final String MESSAGE_NO_VALUE = "Please indicate a task to %1$s";
	protected static final String MESSAGE_NO_FILE = "File not available";

	protected Data data = Data.getInstance();
	protected Display display = Display.getInstance();
	
	public abstract boolean execute();
	
	protected Task getViewTask(int index) throws IndexOutOfBoundsException{
		View view = display.getView();
		
		return view.getTask(index);
	}

}
