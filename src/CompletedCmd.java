
public class CompletedCmd extends Cmd{
	
	int index;
	
	public CompletedCmd(int index){
		this.index = index;
	}
	
	public boolean execute(){
		Data data = Data.getInstance();
		View view = View.getInstance();
		Display display = Display.getInstance();
		
		Task thisTask = view.getTask(index);
		data.update(thisTask, true);
		
		writeToFile();
		
		display.set(view, MESSAGE_EDIT);
		
		return true;
	}
}
