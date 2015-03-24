
public class ViewCmd extends Cmd{

	String view;
	
	public ViewCmd(String view) {
		this.view = view;
	}

	public boolean execute(){
		Display display = Display.getInstance();
		
		display.set(null);
		
	     return true;
	}
}
