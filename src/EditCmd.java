
public class EditCmd extends Cmd{
	
	int index;
	Task task;
	
	public EditCmd(int index, Task task){
		this.index = index;
		this.task = task;
	}
	
	public Display execute(){
		Data myList = Data.getInstance();
		
		if(myList.isEmpty()){
			return new Display(myList.toDateView(), MESSAGE_EMPTY);
		}
		else{
			Task thisTask = myList.getViewTask(index);
			thisTask.update(task);
			writeToFile();
			return new Display(myList.toDateView(), MESSAGE_EDIT);
		}
	}
}
