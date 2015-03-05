
public class DeleteCmd extends Cmd{

	int index;
	
	public DeleteCmd(int index){
		this.index = index;
	}
	
	public String execute(Data myList){
		myList.remove(index);
		writeToFile(myList);
	        
	    //return String.format(MESSAGE_ADD, file, input);
	    return myList.toString();
	}
}
