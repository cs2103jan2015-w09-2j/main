
public class CompletedCmd extends Cmd{
	
	int index;
	
	public CompletedCmd(int index){
		this.index = index;
	}
	
	public Display execute(){
		Data myList = Data.getInstance();
		View myView = View.getInstance();
		
		if(myList.isEmpty()){
			return new Display(myView, MESSAGE_EMPTY);
		}
		else{
			Task thisTask = myView.getTask(index);
			myList.update(thisTask, true);
			writeToFile();
			return new Display(myView, MESSAGE_EDIT);
		}
	}
}
