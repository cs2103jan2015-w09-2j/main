
//@author A0111867A
public class DeleteCmd extends ModifiableCmd{

	private Task task;
	private int index;
	
	public DeleteCmd(int index){
		this.index = index;
		this.task = new Task("");
	}
	
	/**
	 * Execute the command specified in this class
	 */
	public void execute(){
		task = getViewTask(index);
		
		data.remove(task);
		writeToFile();
	     
		String deleteMsg = String.format(DELETE_TASK_MESSAGE, task.getDescription());
		display.set(deleteMsg, index, COMMAND_TYPE.DELETE);
	}
	
	/**
	 * Undo the command previously executed by this class
	 */
	@Override
	public void undo(){
		data.add(task);
		writeToFile();
		
		String undoMessage = String.format(UNDO_DELETE_MESSAGE, task.getDescription(), getTaskType(task));
		display.set(undoMessage, index, COMMAND_TYPE.ADD);
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param o the reference object with which to compare.
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof DeleteCmd){
			DeleteCmd otherDeleteCmd = (DeleteCmd)o;
			return this.task.equals(otherDeleteCmd.task) && this.index == otherDeleteCmd.index;
		}
		else{
			return false;
		}
	}
}
