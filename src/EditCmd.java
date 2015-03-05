
public class EditCmd extends Cmd{
	
	int index;
	Task task;
	
	public EditCmd(int index, Task task){
		this.index = index;
		this.task = task;
	}
	
	public String execute(Data myList){
		if(myList.isEmpty()){
			return String.format(MESSAGE_EMPTY);
		}
		else{
			Task Task = myList.get(index);
			task.update(task);
			writeToFile(myList);
			return myList.toString();
		}
	}
}
