
public class AddCmd extends ModifiableCmd{

	private Task task;
	
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
	
	@Override
	public boolean equals(Object o){
		if(o instanceof AddCmd){
			AddCmd otherAddCmd = (AddCmd)o;
			return this.task.equals(otherAddCmd.task);
		}
		else{
			return false;
		}
	}
}
