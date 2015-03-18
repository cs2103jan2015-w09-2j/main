
public class CompletedCmd extends Cmd{
	
	int index;
	
	public CompletedCmd(int index){
		this.index = index;
	}
	
	public Display execute(Data myList){
		if(myList.isEmpty()){
			return new Display(myList.toDateView(), MESSAGE_EMPTY);
		}
		else{
			Task thisTask = myList.getViewTask(index);
			//thisTask.complete();
			writeToFile(myList);
			return new Display(myList.toDateView(), MESSAGE_EDIT);
		}
	}
}
