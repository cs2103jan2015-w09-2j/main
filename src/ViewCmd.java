
public class ViewCmd extends Cmd{

	String view;
	
	public ViewCmd(String view) {
	//	System.out.println("View is in ViewCmd");
		this.view = view;
		// TODO Auto-generated constructor stub
	}

	public Display execute(Data myList){
	     return new Display(myList.toDateView(), "");
	}
}
