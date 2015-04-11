
public class AddCmd extends ModifiableCmd{

	Task task;
	
	public AddCmd(Task task){
		this.task = task;
	}
	
	public boolean execute(){
		
		data.add(task);
		writeToFile();
	     
		String addMessage = String.format(ADD_TASK_MESSAGE, task.getDescription(), getTaskType(task));
	    display.set(addMessage, COMMAND_TYPE.ADD);
	    
		return true;
	}
	
	public void undo(){
	
		data.remove(task);
		writeToFile();
		
		String undoMessage = String.format(ADD_TASK_MESSAGE, task.getDescription(), getTaskType(task));
	    display.set(undoMessage, COMMAND_TYPE.DELETE);
	
	}
}
