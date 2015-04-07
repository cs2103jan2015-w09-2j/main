

public abstract class Cmd {


	// the task and the category 
	protected static final String ADD_TASK_MESSAGE = "Added <i>%1$s</i> to <i>%2$s</i>";
	
	//task
	protected static final String UNDO_ADD_MESSAGE = "Undo action: \"Add <i>%1$s</i>\"";
	
	//new task desc
	protected static final String EDIT_DES_MESSAGE = "Task description is changed to <i>%1$s</i>";
	
	//current task name, start time, first - new time, second- previous time
	protected static final String EDIT_START_TIME_MESSAGE = "<i>%1$s</i> will begin on <i>%2$s</i> instead of <i>%3$s</i>";

	//task, new end time
	protected static final String EDIT_DEADLINE_MESSAGE = "<i>%1$s</i> is moved to <i>%2$s</i>";
	
	//prev des, new des, end time
	protected static final String EDIT_DES_AND_DEADLINE_MESSAGE = "<p align=\"center\"><i>%1$s</i> is changed to <i>%2$s</i>.</p><p align=\"center\">It is moved to <i>%3$s</i></p>";
	
	//prev des, new des, start time, end time
	protected static final String EDIT_DES_AND_TIME_MESSAGE = "<p align=\"center\"><i>%1$s</i> is changed to <i>%2$s</i>.</p><p align=\"center\">It will begin at<i>%3$s</i> and will end at <i>%3$s</i></p>";
	
	//prev des, new des, start date, end date
	protected static final String EDIT_DES_AND_DATE_MESSAGE = "<p align=\"center\"><i>%1$s</i> is changed to <i>%2$s</i>.</p><p align=\"center\">It will begin at<i>%3$s</i> and will end at <i>%3$s</i></p>";
	
	//prev des, new des, start datetime, end datetime
	protected static final String EDIT_DES_AND_DATE_TIME_MESSAGE = "<p align=\"center\"><i>%1$s</i> is changed to <i>%2$s</i>.</p><p align=\"center\">It will begin at<i>%3$s</i> and will end at <i>%3$s</i></p>";
	
	//prev des, current task category
	protected static final String EDIT_TASK_TO_SOMEDAY_MESSAGE = "<i>%1$s</i> moved from Someday to <i>%2$s</i>";
	
	//prev des, new des, new start date
	protected static final String EDIT_DES_START_DATE_MESSAGE = "<p align=\"center\"><i>%1$s</i> is changed to <i>%2$s</i>.</p><p align=\"center\">It will begin at<i>%3$s</i>";

	//prev des, new des, new start time
	protected static final String EDIT_DES_START_TIME_MESSAGE = "<p align=\"center\"><i>%1$s</i> is changed to <i>%2$s</i>.</p><p align=\"center\">It will begin at<i>%3$s</i>";

	//prev des, new des, new end date
	protected static final String EDIT_DES_END_DATE_MESSAGE = "<p align=\"center\"><i>%1$s</i> is changed to <i>%2$s</i>.</p><p align=\"center\">It will end at<i>%3$s</i>";

	//prev des, new start date 
	protected static final String EDIT_START_DATE_MESSAGE = "<i>%1$s</i>will begin at<i>%2$s</i>";
	
	//prev des,  new end date
	protected static final String EDIT_END_DATE_MESSAGE = "<i>%1$s</i> will end at<i>%2$s</i>";
	
	//prev des, new time
	protected static final String EDIT_END_TIME_MESSAGE = "<i>%1$s</i> will end at<i>%2$s</i>";
	
	//prev des, new des, new category
	protected static final String EDIT_TASK_DEC_AND_TO_SOMEDAY_MESSAGE = "<p align=\"center\"><i>%1$s</i> is changed to <i>%2$s</i>.</p><p align=\"center\">It is moved from Someday to <i>%3$s</i>.</p>";
	
	//des
	protected static final String DELETE_TASK_MESSAGE = "Deleted <i>%1$s</i>";
	
	//des
	protected static final String UNDO_DELETE_MESSAGE = "Undo action: \"Delete <i>%1$s</i>\"";	
	
	//des
	protected static final String COMPLETE_TASK_MESSAGE = "<i>%1$s</i> is marked as completed!";
	
	//des
	protected static final String UNDO_COMPLETED_MESSAGE = "Undo action: Mark <i>%1$s</i> as completed";
	
	//keyword
	protected static final String SEARCH_KEYWORD_IS_EMPTY = "No task contains the keyword <i>%1$s</i>";
	protected static final String UNDO_EMPTY_MESSAGE = "No action to undo";
	protected static final String INVALID_MESSAGE = "<p align=\"center\">You have entered an invalid command.</p><p align=\"center\">Enter a valid command or <i>Help</i> for assistance.</p>";

	protected static final String EMPTY_DES_MESSAGE = "Please enter a task description to add.";
	
	//storage file specified by user
	protected static final String NO_FILE_FOUND_MESSAGE = "The storage file <i>%1$s</i> is not available";
	
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
