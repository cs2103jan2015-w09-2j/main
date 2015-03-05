
public class EditCmd extends Cmd{
	
	int index;
	Task task;
	
	public EditCmd(int index, Task task){
		this.index = index;
		this.task = task;
	}
	
	public Display execute(Data myList){
		if(myList.isEmpty()){
			return Display(new ArrayList<Task>, MESSAGE_EMPTY);
		}
		else{
			Task thisTask = myList.getViewTask(index);
			thisTask.update(task);
			writeToFile(myList);
			return Display(myList.getView(), MESSAGE_EDIT);
		}
	}
}
