
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
	     
		String deleteMsg = String.format(MESSAGE_DELETE, task.getDescription());
		display.setMessage(deleteMsg);

	    return true;
	}
	
	public void undo(){
		data.add(task);
		writeToFile();
		
		String undoMessage = String.format(MESSAGE_UNDO_DELETE, task.getDescription(), getTaskType(task));
		display.setMessage(undoMessage);
	}
}
