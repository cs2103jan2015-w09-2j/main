
public class EditCmd extends Cmd{
	
	int index;
	Task task;
	
	public EditCmd(int index, Task task){
		this.index = index;
		this.task = task;
	}
	
	public boolean execute(){
		Data data = Data.getInstance();
		Display display = Display.getInstance();
		View view = display.getView();
		
		Task thisTask = view.getTask(index);
		data.update(thisTask, task);
		writeToFile();
		
		view.update();
		display.setMessage(MESSAGE_EDIT);
		
		return true;
	}
}
