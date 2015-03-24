
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
	       
		dateView.update();
	    display.setMessage(MESSAGE_ADD);
	    
		return true;
	}
	
}
