
public class DeleteCmd extends Cmd{

	int index;
	
	public DeleteCmd(int index){
		this.index = index;
	}
	
	public Display execute(Data myList){
		Task thisTask = myList.getViewTask(index);
		myList.remove(thisTask);
		writeToFile(myList);
	        
	    return new Display(myList.getView(), MESSAGE_DELETE);
	}
}
