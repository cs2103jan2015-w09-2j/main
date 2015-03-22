
public class ViewCmd extends Cmd{

	String view;
	
	public ViewCmd(String view) {
		this.view = view;
		// TODO Auto-generated constructor stub
	}

	public Display execute(){
		Data myList = Data.getInstance();
		
	     return new Display(myList.toDateView(), "");
	}
}
