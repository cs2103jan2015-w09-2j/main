

public abstract class Cmd {
	
	//task description and header where description is added needs to be returned
	// i.e. Added "Complete homework" to Today
	protected static final String MESSAGE_ADD = "Added \"%1$s\" to %2$s”";
	
	protected static final String MESSAGE_EDIT_DES = "Description of the task \"%1$s\" is changed to %2$s";
	protected static final String MESSAGE_START_TIME = "Start Time of the task \"%1$s\" is changed to %2$s";
	protected static final String MESSAGE_END_TIME = "End Time of the task \"%1$s\" is changed to %2$s";
	protected static final String MESSAGE_EDIT_START_DATE = "Start date of the task \"%1$s\" is changed to %2$s";
	protected static final String MESSAGE_EDIT_END_DATE = "End date of the task \"%1$s\" is changed to %2$s";
	
	
	//Instead of an undo message, just send back the edit?
	protected static final String MESSAGE_UNDO_EDIT_DES = "Undo Edit: Description reverted back to %1$s";
	protected static final String MESSAGE_UNDO_EDIT_TIME = "Undo Edit: Time is reverted back to %1$s";
	protected static final String MESSAGE_UNDO_EDIT_DATE = "Undo Edit: Date is reverted back to %1$s";

	protected static final String MESSAGE_UNDO_ADD = "Undo Add: \"%1$s\" is removed from task list ";
	
	//i.e. Lecture is added back to today 
	protected static final String MESSAGE_UNDO_DELETE = "Undo Delete: \"%1$s\" is added back to %2$s";
	protected static final String MESSAGE_UNDO_COMPLETED = "Undo Complete: \"%1$s\" is marked as undone";
	
	//
	protected static final String MESSAGE_DELETE = "Deleted \"%1$s\"";
	protected static final String MESSAGE_COMPLETED = "\"%1$s\" marked as completed!";

//	protected static final String MESSAGE_CLEAR = "all content deleted from %1$s";
//	protected static final String MESSAGE_EMPTY = "%1$s is empty";
	
	protected static final String MESSAGE_EMPTY_SEARCH = "Keyword \"%1$s\" is not found";
	protected static final String MESSAGE_UNDO_EMPTY = "No action to undo";
	protected static final String MESSAGE_INVALID = "You have entered an invalid command. Enter a valid command or \"Help\" for assistance.";

	protected static final String MESSAGE_NO_VALUE = "Please indicate a valid task description!";
	protected static final String MESSAGE_NO_FILE = "Storage file is not available";

	protected Data data = Data.getInstance();
	protected Display display = Display.getInstance();
	
	public abstract boolean execute();
	
	public void undo(){
		execute();
	}
	
	protected Task getViewTask(int index) throws IndexOutOfBoundsException{
		View view = display.getView();
		
		return view.getTask(index);
	}

}
