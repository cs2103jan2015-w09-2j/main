
public class AddCmd extends Cmd{

	Task task;
	
	public AddCmd(Task task){
		this.task = task;
	}
	
	public Display execute(){
		Data myList = Data.getInstance();
		
		myList.add(task);
		writeToFile();
	       
		DateView dateView = DateView.getInstance();
		Data data = Data.getInstance();
		
		dateView.set(data.getToday(), data.getUpcoming(), data.getSomeday());
	    
		return new Display(dateView, MESSAGE_ADD);
	}
}
