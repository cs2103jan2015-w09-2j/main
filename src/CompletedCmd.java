
public class CompletedCmd extends Cmd{
	
	int index;
	
	public CompletedCmd(int index){
		this.index = index;
	}
	
	public boolean execute(){
		Data data = Data.getInstance();
		Display display = Display.getInstance();
		View view = display.getView();
		
		Task thisTask = view.getTask(index);
		data.update(thisTask, true);
		
		writeToFile();
		
		display.set(new CompletedView(), MESSAGE_EDIT);
		
		return true;
	}
}
