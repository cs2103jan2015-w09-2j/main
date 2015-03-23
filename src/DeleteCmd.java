
public class DeleteCmd extends Cmd{

	int index;
	
	public DeleteCmd(int index){
		System.out.println("Successfully into Delete Cmd");
		this.index = index;
	}
	
	public Display execute(Data myList){
		Task thisTask = myList.getViewTask(index);
		myList.remove(thisTask);
		writeToFile(myList);
	        
	    return new Display(myList.toDateView(), MESSAGE_DELETE);
	}
}
