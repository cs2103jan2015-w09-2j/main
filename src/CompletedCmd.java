
public class CompletedCmd extends ModifiableCmd{
	
	private Task task;
	private int index;
	
	public CompletedCmd(int index){		
		this.index = index;
	}
	
	public boolean execute() throws IndexOutOfBoundsException{	
		task = getViewTask(index);
		
		task.setIsCompleted(true);
		writeToFile();
		
		String completeMessage = String.format(COMPLETE_TASK_MESSAGE, task.getDescription());
		display.set(completeMessage, index, COMMAND_TYPE.DONE);

		return true;
	}
	
	public void undo(){
		task.setIsCompleted(false);
		writeToFile();
		
		String undoCompleteMessage = String.format(COMPLETE_TASK_MESSAGE, task.getDescription());
		display.set(undoCompleteMessage, index, COMMAND_TYPE.DONE);
	}
	
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
