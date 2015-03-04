
public class AddCmd extends Cmd{

	Task task;
	
	public AddCmd(Task task){
		this.task = task;
	}
	
	public String execute(Data myList){
		 myList.add(task);
		 writeToFile(myList);
	        
	     //return String.format(MESSAGE_ADD, file, input);
	     return myList.toString();
	}
}
