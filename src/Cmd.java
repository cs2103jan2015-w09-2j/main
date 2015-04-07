

public abstract class Cmd {


	protected static final String MESSAGE_ADD = "Added <i>%1$s</i> to <i>%2$s<i>";
	protected static final String MESSAGE_UNDO_ADD = "Undo Add: <i>%1$s</i> is removed from task list ";
	
	protected static final String MESSAGE_EDIT_DES = "Detail of the task change from <i>%1$s</i> to <i>%2$s</i>";
	protected static final String MESSAGE_START_TIME = "Start Time of the task <i>%1$s</i>  is changed to <i>%2$s</i>";
	protected static final String MESSAGE_END_TIME = "End Time of the task </i>%1$s is changed to %2$s</i>";
	protected static final String MESSAGE_EDIT_START_DATE = "Start date of the task <i>%1$s</i> is changed to <i>%2$s</i>";
	protected static final String MESSAGE_EDIT_END_DATE = "End date of the task <i>%1$s</i> is changed to <i>%2$s</i>";
	
	
	protected static final String MESSAGE_UNDO_EDIT_DES = "Undo Edit: Reverted back to <i>%1$s</i>";
	protected static final String MESSAGE_UNDO_EDIT_TIME = "Undo Edit: Time is reverted back to <i>%1$s</i>";
	protected static final String MESSAGE_UNDO_EDIT_DATE = "Undo Edit: Date is reverted back to <i>%1$s</i>";

	protected static final String MESSAGE_DELETE = "Deleted <i>%1$s</i>";
	protected static final String MESSAGE_UNDO_DELETE = "Undo Delete: <i>%1$s</i> is added back to %2$s";	
	
	protected static final String MESSAGE_COMPLETED = "<i>%1$s</i> marked as completed!";
	protected static final String MESSAGE_UNDO_COMPLETED = "Undo Complete: <i>%1$s</i> is marked as undone";

	
	protected static final String MESSAGE_EMPTY_SEARCH = "Keyword <i>%1$s</i> is not found";
	protected static final String MESSAGE_UNDO_EMPTY = "No action to undo";
	protected static final String MESSAGE_INVALID = "You have entered an invalid command. Enter a valid command or <i>Help</i> for assistance.";

	protected static final String MESSAGE_NO_VALUE = "Please indicate a valid task description!";
	protected static final String MESSAGE_NO_FILE = "Storage file is not available";

	private static final String TASKTYPE_TODAY = "Today";
	private static final String TASKTYPE_UPCOMING = "Upcoming";
	private static final String TASKTYPE_SOMEDAY = "Someday";
	
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

	protected static String getTaskType(Task task){
		String taskType = "";
		
		if(task.isTodayTask()){
			taskType = TASKTYPE_TODAY;
		}
		else if(task.isUpcomingTask()){
			taskType = TASKTYPE_UPCOMING;
		}
		else{
			assert(task.isSomedayTask());
			taskType = TASKTYPE_SOMEDAY;
		}
		
		return taskType;
	}
}
