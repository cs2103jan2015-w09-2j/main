
public class AddCmd extends Cmd{

	Task task;
	
	public AddCmd(Task task){
		this.task = task;
	}
	
	public Display execute(){
		Data myList = Data.getInstance();
		
		myList.add(task);
		writeToFile();
	        
	    return new Display(myList.toDateView(), MESSAGE_ADD);
	}
}
