
public class DeleteCmd extends Cmd{

	private Task task;
	
	public DeleteCmd(int index){
		View view = display.getView();
		
		this.task = view.getTask(index);
	}
	
	public boolean execute(){
		if(task == null){
			display.setMessage(MESSAGE_INVALID_INDEX);
		}
		else{
			data.remove(task);
			writeToFile();
		     
			display.setMessage(MESSAGE_DELETE);
		}
	    return true;
	}
}
