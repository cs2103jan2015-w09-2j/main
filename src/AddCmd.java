
public class AddCmd extends Cmd{

	Task task;
	
	public AddCmd(Task task){
		this.task = task;
	}
	
	public Display execute(Data myList){
		 myList.add(task);
		 writeToFile(myList);
	        
	     return new Display(myList.getView(), MESSAGE_ADD);
	}
}
