
public class AddCmd extends Cmd{

	Task task;
	
	public AddCmd(Task task){
		this.task = task;
	}
	
	public boolean execute(){
		Data data = Data.getInstance();
		Display display = Display.getInstance();
		
		data.add(task);
		writeToFile();
	       
	    display.setMessage(MESSAGE_ADD);
	    
		return true;
	}
	
}
