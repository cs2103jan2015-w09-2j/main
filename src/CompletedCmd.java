
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
		
		display.setMessage(MESSAGE_COMPLETED);

		return true;
	}
	
	public void undo(){
		data.update(task, false);
		writeToFile();
		
		display.setMessage(MESSAGE_UNDO_COMPLETED);
	}
}
