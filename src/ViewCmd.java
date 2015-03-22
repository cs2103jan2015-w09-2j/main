
public class ViewCmd extends Cmd{

	String view;
	
	public ViewCmd(String view) {
		this.view = view;
		// TODO Auto-generated constructor stub
	}

	public Display execute(Data myList){
	     return new Display(myList.toDateView(), "");
	}
}
