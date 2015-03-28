
public class EditCmd extends Cmd{
	
	private Task task;
	private Task editContent;
	
	public EditCmd(int index, Task editContent){
		View view = display.getView();
		
		this.task = view.getTask(index);
		this.editContent = editContent;
	}
	
	public boolean execute(){
		if(task == null){
			display.setMessage(MESSAGE_INVALID_INDEX);
		}
		else{
			data.update(task, editContent);
			writeToFile();
			
			display.setMessage(MESSAGE_EDIT);
		}
		return true;
	}
}
