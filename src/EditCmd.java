
public class EditCmd extends Cmd{
	
	int index;
	Task task;
	
	public EditCmd(int index, Task task){
		this.index = index;
		this.task = task;
	}
	
	public Display execute(){
		Data myList = Data.getInstance();
		View myView = View.getInstance();
		
		if(myList.isEmpty()){
			return new Display(myView, MESSAGE_EMPTY);
		}
		else{
			Task thisTask = myView.getTask(index);
			thisTask.update(task);
			writeToFile();
			return new Display(myView, MESSAGE_EDIT);
		}
	}
}
