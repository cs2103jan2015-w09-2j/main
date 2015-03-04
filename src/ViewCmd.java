
public class ViewCmd extends Cmd{

	public ViewCmd(){}
	
	public String execute(Data myList){
	     return myList.toString();
	}
}