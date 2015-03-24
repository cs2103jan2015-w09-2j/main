
public class DeleteCmd extends Cmd{

	int index;
	
	public DeleteCmd(int index){
		this.index = index;
	}
	
	public boolean execute(){
		Data data = Data.getInstance();
		View view = View.getInstance();
		Display display = Display.getInstance();
		
		Task thisTask = view.getTask(index);
		data.remove(thisTask);
		writeToFile();
	     
		display.set(view, MESSAGE_DELETE);
		
	    return true;
	}
}
