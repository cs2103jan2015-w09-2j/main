
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
	     
		display.setMessage(MESSAGE_DELETE);

	    return true;
	}
	
	public void undo(){
		data.add(task);
		writeToFile();
		
		display.setMessage(MESSAGE_UNDO_DELETE);
	}
}
