
public class DeleteCmd extends Cmd{

	int index;
	
	public DeleteCmd(int index){
		this.index = index;
	}
	
	public boolean execute(){
		Data data = Data.getInstance();
		Display display = Display.getInstance();
		View view = display.getView();
		
		Task thisTask = view.getTask(index);
		data.remove(thisTask);
		writeToFile();
	     
		
		display.setMessage(MESSAGE_DELETE);
		
	    return true;
	}
}
