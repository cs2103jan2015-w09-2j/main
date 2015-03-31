
public class EditCmd extends ModifiableCmd{
	
	private Task task;
	private Task editContent;
	private int index;
	
	public EditCmd(int index, Task editContent){
		this.index = index;
		this.editContent = editContent;
	}
	
	public boolean execute() throws IndexOutOfBoundsException{
		Task tempTask;
		
		task = getViewTask(index);
		tempTask = clone(task);
		
		data.update(task, editContent);
		writeToFile();
		editContent = tempTask;
		
		display.setMessage(MESSAGE_EDIT);
		
		return true;
	}
	
	public void undo(){
		task.setDescription(editContent.getDescription());
		task.setStart(editContent.getStart());
		task.setEnd(editContent.getEnd());
		task.setIsCompleted(editContent.getIsCompleted());
		
		writeToFile();
		
		display.setMessage(MESSAGE_UNDO_EDIT);
	}
		
	private Task clone(Task task){
		Task clonedTask = new Task();
		clonedTask.setDescription(task.getDescription());
		clonedTask.setStart(task.getStart());
		clonedTask.setEnd(task.getEnd());
		clonedTask.setIsCompleted(task.getIsCompleted());
		
		return clonedTask;
	}
}