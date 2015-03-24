
public class EditCmd extends Cmd{
	
	int index;
	Task task;
	
	public EditCmd(int index, Task task){
		this.index = index;
		this.task = task;
	}
	
	public boolean execute(){
		Data data = Data.getInstance();
		View view = View.getInstance();
		Display display = Display.getInstance();
		
		Task thisTask = view.getTask(index);
		data.update(thisTask, task);
		writeToFile();
		
		display.set(view, MESSAGE_EDIT);
		
		return true;
	}
}
