
public class AddCmd extends Cmd{

	Task task;
	
	public AddCmd(Task task){
		this.task = task;
	}
	
	public boolean execute(){
		Data data = Data.getInstance();
		DateView dateView = DateView.getInstance();
		Display display = Display.getInstance();
		
		data.add(task);
		writeToFile();
	       
		dateView.set(data.getToday(), data.getUpcoming(), data.getSomeday());
	    display.set(dateView, MESSAGE_ADD);
	    
		return true;
	}
	
}
