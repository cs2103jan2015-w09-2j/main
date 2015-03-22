
public class CompletedCmd extends Cmd{
	
	int index;
	
	public CompletedCmd(int index){
		this.index = index;
	}
	
	public Display execute(){
		Data myList = Data.getInstance();
		
		if(myList.isEmpty()){
			return new Display(myList.toDateView(), MESSAGE_EMPTY);
		}
		else{
			Task thisTask = myList.getViewTask(index);
			thisTask.setIsCompleted(true);
			writeToFile();
			return new Display(myList.toDateView(), MESSAGE_EDIT);
		}
	}
}
