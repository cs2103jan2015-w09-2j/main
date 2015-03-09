
public class ViewCmd extends Cmd{

	public ViewCmd(){}
	
	public Display execute(Data myList){
	     return new Display(myList.toDateView(), "");
	}
}