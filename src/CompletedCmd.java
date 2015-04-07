
public class CompletedCmd extends ModifiableCmd{
	
	private Task task;
	private int index;
	
	public CompletedCmd(int index){		
		this.index = index;
	}
	
	public boolean execute() throws IndexOutOfBoundsException{	
		task = getViewTask(index);
		
		data.update(task, true);
		writeToFile();
		
		String completeMessage = String.format(COMPLETE_TASK_MESSAGE, task.getDescription());
		display.setMessage(completeMessage);

		return true;
	}
	
	public void undo(){
		data.update(task, false);
		writeToFile();
		
		String undoCompleteMessage = String.format(COMPLETE_TASK_MESSAGE, task.getDescription());
		display.setMessage(undoCompleteMessage);
	}
}
