
public class DeleteCmd extends Cmd{

	int index;
	
	public DeleteCmd(int index){
		this.index = index;
	}
	
	public Display execute(){
		Data myList = Data.getInstance();
		
		Task thisTask = myList.getViewTask(index);
		myList.remove(thisTask);
		writeToFile();
	        
	    return new Display(myList.toDateView(), MESSAGE_DELETE);
	}
}
