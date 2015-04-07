

public abstract class Cmd {


	protected static final String ADD_TASK_MESSAGE = "Added <i>%1$s</i> to <i>%2$s</i>";
	protected static final String UNDO_ADD_MESSAGE = "Undo action: \"Add <i>%1$s</i>\"";
	
	protected static final String EDIT_DES_MESSAGE = "Task description is changed to <i>%1$s</i>";
	protected static final String EDIT_START_TIME_MESSAGE = "<i>%1$s</i> will begin on <i>%2$s</i> instead of <i>%3$s</i>";
	protected static final String EDIT_DEADLINE_MESSAGE = "<i>%1$s</i> is moved to <i>%2$s</i>";
	protected static final String EDIT_DES_AND_DEADLINE_MESSAGE = "<p align=\"center\"><i>%1$s</i> is changed to <i>%2$s</i>.</p><p align=\"center\">It is moved from to <i>%3$s</i></p>";
	protected static final String EDIT_DES_AND_TIME_MESSAGE = "<p align=\"center\">Task description is changed to <i>%1$s</i>.</p><p align=\"center\">It will begin at<i>%2$s</i> and will end at <i>%3$s</i></p>";
	protected static final String EDIT_DES_AND_DATE_MESSAGE = "<p align=\"center\">Task description is changed to <i>%1$s</i>.</p><p align=\"center\">It will begin at<i>%2$s</i> and will end at <i>%3$s</i></p>";
	protected static final String EDIT_TASK_TO_SOMEDAY_MESSAGE = "<i>%1$s</i> is changed from the category <i>%2$s</i> to Someday";
	protected static final String EDIT_TASK_DES_START_DATE_MESSAGE = "<p align=\"center\">Task description is changed to <i>%1$s</i>.</p><p align=\"center\">It will begin at<i>%2$s</i>";
	protected static final String EDIT_TASK_DES_END_DATE_MESSAGE = "<p align=\"center\">Task description is changed to <i>%1$s</i>.</p><p align=\"center\">It will end at<i>%2$s</i>";
	protected static final String EDIT_TASK_START_DATE_MESSAGE = "<i>%1$s</i>will begin at<i>%2$s</i>";
	protected static final String EDIT_TASK_END_DATE_MESSAGE = "<i>%1$s</i> will end at<i>%2$s</i>";
	protected static final String EDIT_TASK_DEC_AND_TO_SOMEDAY_MESSAGE = "<i>%1$s</i> is changed from the category <i>%2$s</i> to Someday";
	
	protected static final String DELETE_TASK_MESSAGE = "Deleted <i>%1$s</i>";
	protected static final String UNDO_DELETE_MESSAGE = "Undo action: \"Delete <i>%1$s</i>\"";	
	
	protected static final String COMPLETE_TASK_MESSAGE = "<i>%1$s</i> is marked as completed!";
	protected static final String UNDO_COMPLETED_MESSAGE = "Undo action: Mark <i>%1$s</i> as completed";

	protected static final String SEARCH_KEYWORD_IS_EMPTY = "No task contains the keyword <i>%1$s</i>";
	protected static final String UNDO_EMPTY_MESSAGE = "No action to undo";
	protected static final String INVALID_MESSAGE = "<p align=\"center\">You have entered an invalid command.</p><p align=\"center\">Enter a valid command or <i>Help</i> for assistance.</p>";

	protected static final String EMPTY_DES_MESSAGE = "Please enter a task description to add.";
	protected static final String NO_FILE_FOUND_MESSAGE = "The storage file is not available";
	
	private static final String TODAY_TASK = "Today";
	private static final String UPCOMING_TASK = "Upcoming";
	private static final String SOMEDAY_TASK = "Someday";
	
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
			taskType = TODAY_TASK;
		}
		else if(task.isUpcomingTask()){
			taskType = UPCOMING_TASK;
		}
		else{
			assert(task.isSomedayTask());
			taskType = SOMEDAY_TASK;
		}
		
		return taskType;
	}
}
