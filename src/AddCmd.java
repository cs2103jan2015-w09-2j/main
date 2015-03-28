
public class AddCmd extends ModifiableCmd{

	Task task;
	
	public AddCmd(Task task){
		this.task = task;
	}
	
	public boolean execute(){
		data.add(task);
		writeToFile();
	       
	    display.setMessage(MESSAGE_ADD);
	    
		return true;
	}
	
	public void undo(){
		data.remove(task);
		writeToFile();
		
	    display.setMessage(MESSAGE_UNDO_ADD);
	}
}
