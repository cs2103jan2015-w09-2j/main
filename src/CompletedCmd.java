//@author A0111867A
public class CompletedCmd extends ModifiableCmd{
	
	private Task task;
	private int index;
	
	public CompletedCmd(int index){		
		this.index = index;
		this.task = new Task("");
	}
	
	/**
	 * Execute the command specified in this class
	 */
	public void execute() throws IndexOutOfBoundsException{	
		task = getViewTask(index);
		
		task.setIsCompleted(true);
		writeToFile();
		
		String completeMessage = String.format(COMPLETE_TASK_MESSAGE, task.getDescription());
		display.set(completeMessage, index, COMMAND_TYPE.DONE);
	}
	
	/**
	 * Undo the command previously executed by this class
	 */
	@Override
	public void undo(){
		System.out.println(task);
		task.setIsCompleted(false);
		writeToFile();
		
		String undoCompleteMessage = String.format(UNDO_COMPLETED_MESSAGE, task.getDescription());
		display.set(undoCompleteMessage, index, COMMAND_TYPE.DONE);
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param o the reference object with which to compare.
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof CompletedCmd){
			CompletedCmd otherCompletedCmd = (CompletedCmd)o;
			return this.task.equals(otherCompletedCmd.task) && this.index == otherCompletedCmd.index;
		}
		else{
			return false;
		}
	}
}
