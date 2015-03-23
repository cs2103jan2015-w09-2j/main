
public class DeleteCmd extends Cmd{

	int index;
	
	public DeleteCmd(int index){
		this.index = index;
	}
	
	public Display execute(){
		Data myList = Data.getInstance();
		View myView = View.getInstance();
		
		Task thisTask = myView.getTask(index);
		myList.remove(thisTask);
		writeToFile();
	        
	    return new Display(myView, MESSAGE_DELETE);
	}
}
