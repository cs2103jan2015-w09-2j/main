
public class AddCmd extends ModifiableCmd{

	Task task;
	
	public AddCmd(Task task){
		this.task = task;
	}
	
	public boolean execute(){
		
		data.add(task);
		writeToFile();
	     
		String addMessage = String.format(MESSAGE_ADD, task.getDescription(), getTaskType(task));
	    display.setMessage(addMessage);
	    
		return true;
	}
	
	public void undo(){
	
		data.remove(task);
		writeToFile();
		
		String undoMessage = String.format(MESSAGE_UNDO_ADD, task.getDescription(), getTaskType(task));
	    display.setMessage(undoMessage);
	
	}
}
