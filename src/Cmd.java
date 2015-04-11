
public abstract class Cmd {

	// the task and the category
	protected static final String ADD_TASK_MESSAGE = "Added <font color=\"#CC3300\"><i>%1$s</i></font> to <font color=\"#CC3300\"><i>%2$s</i></font>";

	// task
	protected static final String UNDO_ADD_MESSAGE = "Undo action: <font color=\"#CC3300\"><i>\"Add %1$s\"</i></font>";

//----------------------------------edit messages-------------------------------------------------------------------
	// new task desc
	protected static final String EDIT_DES_MESSAGE = "Task description is changed to <font color=\"#CC3300\"><i>%1$s</i></font>";
	
	// current task name, start time, first - new time, second- previous time
	protected static final String EDIT_START_TIME_MESSAGE = "<font color=\"#CC3300\"><i>%1$s</i></font> will begin on <font color=\"#CC3300\"><i>%2$s</i></font> instead of <font color=\"#CC3300\"><i>%3$s</i></font>";

	// task, new end time
	protected static final String EDIT_DES_END_TIME_MESSAGE = "<font color=\"#CC3300\"><i>%1$s</i></font> is moved to <font color=\"#CC3300\"><i>%2$s</i></font>";

	// prev des, new des, end time
	protected static final String EDIT_DES_AND_DEADLINE_MESSAGE = "<p align=\"center\"><font color=\"#CC3300\"><i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It is moved to <font color=\"#CC3300\"><i>%3$s</i></font></p>";

	// prev des, new des, start time, end time
	protected static final String EDIT_DES_AND_TIME_MESSAGE = "<p align=\"center\"><font color=\"#CC3300\"><i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It will begin at <font color=\"#CC3300\"><i>%3$s</i></font> and will end at <font color=\"#CC3300\"><i>%4$s</i></p></font>";

	// prev des, new des, start date, end date
	protected static final String EDIT_DES_AND_DATE_MESSAGE = "<p align=\"center\"><font color=\"#CC3300\"><i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It will begin at <font color=\"#CC3300\"><i>%3$s</i></font> and will end at <font color=\"#CC3300\"><i>%3$s</i></font></p>";

	// prev des, new des, start datetime, end datetime
	protected static final String EDIT_DES_AND_DATE_TIME_MESSAGE = "<p align=\"center\"><font color=\"#CC3300\"><i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It will begin at <font color=\"#CC3300\"><i>%3$s</i></font> and will end at <font color=\"#CC3300\"><i>%4$s</i></font></p>";

	// prev des, current task category
	protected static final String EDIT_TASK_TO_SOMEDAY_MESSAGE = "<font color=\"#CC3300\"><i>%1$s</i></font> moved from <font color=\"#CC3300\"><i>%2$s</i></font> to Someday";

		
	// prev des, new des, new start date
	protected static final String EDIT_DES_START_DATE_MESSAGE = "<p align=\"center\"><font color=\"#CC3300\"><i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It will begin at <font color=\"#CC3300\"><i>%3$s</i></font>";

	// prev des, new des, new start time
	protected static final String EDIT_DES_START_TIME_MESSAGE = "<p align=\"center\"><font color=\"#CC3300\"><i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It will begin at <font color=\"#CC3300\"><i>%3$s</i></font>";

	// prev des, new des, new end date
	protected static final String EDIT_DES_END_DATE_MESSAGE = "<p align=\"center\"><font color=\"#CC3300\"><i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It will end at <font color=\"#CC3300\"><i>%3$s</i></font>";

	// prev des, new start date
	protected static final String EDIT_START_DATE_MESSAGE = "<font color=\"#CC3300\"><i>%1$s</i></font> will begin at <font color=\"#CC3300\"><i>%2$s</i></font>";

	// prev des, new end date
	protected static final String EDIT_END_DATE_MESSAGE = "<font color=\"#CC3300\"><i>%1$s</i></font> will end at <font color=\"#CC3300\"><i>%2$s</i></font>";

	// prev des, new time
	protected static final String EDIT_END_TIME_MESSAGE = "<font color=\"#CC3300\"><i>%1$s</i></font> will end at <font color=\"#CC3300\"><i>%2$s</i></font>";


	// prev des, new start time, new end time
	protected static final String EDIT_TIME_MESSAGE = "<font color=\"#CC3300\"><i>%1$s</i></font> will start at <font color=\"#CC3300\"><i>%2$s</i></font> and will end at <font color=\"#CC3300\"><i>%2$s</i></font>";

	// prev des, new des, new category
	protected static final String EDIT_TASK_DEC_AND_TO_SOMEDAY_MESSAGE = "<p align=\"center\"><font color=\"#CC3300\"><i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It is moved from<font color=\"#CC3300\"><i>%3$s</i></font> to Someday.</p>";

	//----------------------------------edit messages-------------------------------------------------------------------
	

	//----------------------------------undo edit messages-------------------------------------------------------------------
		// new task desc
		protected static final String UNDO_EDIT_DES_MESSAGE = "Undo action: Task description is changed to <font color=\"#CC3300\"><i>%1$s</i></font>";
		
		// current task name, start time, first - new time, second- previous time
		protected static final String UNDO_EDIT_START_TIME_MESSAGE = "<font color=\"#CC3300\">Undo action: <i>%1$s</i></font> will begin on <font color=\"#CC3300\"><i>%2$s</i></font> instead of <font color=\"#CC3300\"><i>%3$s</i></font>";

		// task, new end time
		protected static final String UNDO_EDIT_DEADLINE_MESSAGE = "<font color=\"#CC3300\">Undo action: <i>%1$s</i></font> is moved to <font color=\"#CC3300\"><i>%2$s</i></font>";

		// prev des, new des, end time
		protected static final String UNDO_EDIT_DES_AND_DEADLINE_MESSAGE = "<p align=\"center\">Undo action: <font color=\"#CC3300\"><i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It is moved to <font color=\"#CC3300\"><i>%3$s</i></font></p>";

		// prev des, new des, start time, end time
		protected static final String UNDO_EDIT_DES_AND_TIME_MESSAGE = "<p align=\"center\">Undo action: <font color=\"#CC3300\"><i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It will begin at <font color=\"#CC3300\"><i>%3$s</i></font> and will end at <font color=\"#CC3300\"><i>%4$s</i></p></font>";

		// prev des, new des, start date, end date
		protected static final String UNDO_EDIT_DES_AND_DATE_MESSAGE = "<p align=\"center\">Undo action: <font color=\"#CC3300\"><i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It will begin at <font color=\"#CC3300\"><i>%3$s</i></font> and will end at <font color=\"#CC3300\"><i>%3$s</i></font></p>";

		// prev des, new des, start datetime, end datetime
		protected static final String UNDO_EDIT_DES_AND_DATE_TIME_MESSAGE = "<p align=\"center\">Undo action: <font color=\"#CC3300\"><i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It will begin at <font color=\"#CC3300\"><i>%3$s</i></font> and will end at <font color=\"#CC3300\"><i>%4$s</i></font></p>";

		// prev des, current task category
		protected static final String UNDO_EDIT_TASK_TO_SOMEDAY_MESSAGE = "<font color=\"#CC3300\">Undo action: <i>%1$s</i></font> moved from Someday to <font color=\"#CC3300\"><i>%2$s</i></font>";

		// prev des, new des, new start date
		protected static final String UNDO_EDIT_DES_START_DATE_MESSAGE = "<p align=\"center\"><font color=\"#CC3300\">Undo action: <i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It will begin at <font color=\"#CC3300\"><i>%3$s</i></font>";

		// prev des, new des, new start time
		protected static final String UNDO_EDIT_DES_START_TIME_MESSAGE = "<p align=\"center\"><font color=\"#CC3300\">Undo action: <i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It will begin at <font color=\"#CC3300\"><i>%3$s</i></font>";

		// prev des, new des, new end date
		protected static final String UNDO_EDIT_DES_END_DATE_MESSAGE = "<p align=\"center\"><font color=\"#CC3300\">Undo action: <i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It will end at <font color=\"#CC3300\"><i>%3$s</i></font>";

		// prev des, new start date
		protected static final String UNDO_EDIT_START_DATE_MESSAGE = "Undo action: <font color=\"#CC3300\"><i>%1$s</i></font> will begin at <font color=\"#CC3300\"><i>%2$s</i></font>";

		// prev des, new end date
		protected static final String UNDO_EDIT_END_DATE_MESSAGE = "Undo action: <font color=\"#CC3300\"><i>%1$s</i></font> will end at <font color=\"#CC3300\"><i>%2$s</i></font>";

		// prev des, new time
		protected static final String UNDO_EDIT_END_TIME_MESSAGE = "Undo action: <font color=\"#CC3300\"><i>%1$s</i></font> will end at <font color=\"#CC3300\"><i>%2$s</i></font>";

		// prev des, new des, new category
		protected static final String UNDO_EDIT_TASK_DEC_AND_TO_SOMEDAY_MESSAGE = "<p align=\"center\">Undo action: <font color=\"#CC3300\"><i>%1$s</i></font> is changed to <font color=\"#CC3300\"><i>%2$s</i></font>.</p><p align=\"center\">It is moved from Someday to <font color=\"#CC3300\"><i>%3$s</i></font>.</p>";

		//---------------------------------- undo edit messages-------------------------------------------------------------------
	
	// des
	protected static final String DELETE_TASK_MESSAGE = "Deleted <font color=\"#CC3300\"><i>%1$s</i></font>";

	// des
	protected static final String UNDO_DELETE_MESSAGE = "Undo action: <font color=\"#CC3300\"><i>\"Delete %1$s\"</i></font>";

	// des
	protected static final String COMPLETE_TASK_MESSAGE = "<font color=\"#CC3300\"><i>%1$s</i> is marked as completed!";

	// des
	protected static final String UNDO_COMPLETED_MESSAGE = "Undo action: <font color=\"#CC3300\"><i>\"Mark %1$s as completed\"</i></font>";

	// keyword
	protected static final String SEARCH_KEYWORD_IS_EMPTY = "No task contains the keyword <font color=\"#CC3300\"><i>%1$s</i></font>";
	protected static final String UNDO_EMPTY_MESSAGE = "No action to undo";
	protected static final String INVALID_MESSAGE = "<p align=\"center\">You have entered an invalid command.</p><p align=\"center\">Enter a valid command or <font color=\"#CC3300\"><i>Help</i></font> for assistance.</p>";

	protected static final String EMPTY_DES_MESSAGE = "Please enter a task description to add.";

	// storage file specified by user
	protected static final String NO_FILE_FOUND_MESSAGE = "The storage file <font color=\"#CC3300\"><i>%1$s</i></font> is not available";

	private static final String TODAY_TASK = "Today";
	private static final String UPCOMING_TASK = "Upcoming";
	private static final String SOMEDAY_TASK = "Someday";

	protected static final String MESSAGE_SAVE_CONFIG_NOT_FOUND = "config.json file not found";
	protected static final String MESSAGE_SAVE_NEW_USER_DIRECTORY = "Directory has been set to <font color=\"#CC3300\"><i>%1$s</i></font> ";
	protected static final String MESSAGE_UNDO_SAVE = "Undo save: old file location restored";
	protected static final String MESSAGE_STORAGE_FILE_NOT_FOUND = "OneTag.json is not found!\r\n";
	protected static final String NAME_CLASS_STORAGE = "storage";
	protected static final String MESSAGE_FILE_ACCESS_NOT_ALLOWED = "Error with oneTag.json file access";
	protected Data data = Data.getInstance();
	protected Display display = Display.getInstance();
	protected OneTagLogger logger = OneTagLogger.getInstance();

	public abstract boolean execute();

	public void undo() {
		execute();
	}

	protected Task getViewTask(int index) throws IndexOutOfBoundsException {
		View view = display.getView();

		return view.getTask(index);
	}

	protected static String getTaskType(Task task) {
		String taskType = "";

		if (task.isTodayTask()) {
			taskType = TODAY_TASK;
		} else if (task.isUpcomingTask()) {
			taskType = UPCOMING_TASK;
		} else {
			assert (task.isSomedayTask());
			taskType = SOMEDAY_TASK;
		}

		return taskType;
	}
}
