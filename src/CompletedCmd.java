
public class CompletedCmd extends ModifiableCmd{
	
	private Task task;
	
	public CompletedCmd(int index){
		View view = display.getView();
		
		this.task = view.getTask(index);
	}
	
	public boolean execute(){		
		if(task == null){
			display.setMessage(MESSAGE_INVALID_INDEX);
		}
		else{
			data.update(task, true);
			
			writeToFile();
			
			display.setMessage(MESSAGE_COMPLETED);
		}
		
		return true;
	}
	
	public void undo(){
		data.update(task, false);
		writeToFile();
		
		display.setMessage(MESSAGE_UNDO_COMPLETED);
	}
}
