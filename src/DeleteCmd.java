
public class DeleteCmd extends ModifiableCmd{

	private Task task;
	private int index;
	
	public DeleteCmd(int index){
		this.index = index;
	}
	
	public boolean execute(){
		task = getViewTask(index);
		
		data.remove(task);
		writeToFile();
	     
		String deleteMsg = String.format(DELETE_TASK_MESSAGE, task.getDescription());
		display.set(deleteMsg, index, COMMAND_TYPE.DELETE);

	    return true;
	}
	
	public void undo(){
		data.add(task);
		writeToFile();
		
		String undoMessage = String.format(UNDO_DELETE_MESSAGE, task.getDescription(), getTaskType(task));
		display.set(undoMessage, index, COMMAND_TYPE.ADD);
	}
}
