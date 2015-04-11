//@author A0111867A

public class Display {

	private static Display display = null;
	private View view;
	private String message = "";
	private int viewIndex = -1;
	private COMMAND_TYPE command = COMMAND_TYPE.HOME;
	
	private Display(){
		this.view = new HomeView();		
	}
	
	public static Display getInstance() {
		if (display == null) {
			display = new Display();
		}
		return display;
	}
	
	public View getView() {
		return view;
	}

	public String getMessage() {
		return message;
	}
		
	public int getViewIndex() {
		return viewIndex;
	}

	public COMMAND_TYPE getCommand(){
		return command;
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	public void setMessage(String message) {
		this.message = message;
		this.view.update();
	}
	
	public void set(View view, String message) {
		this.view = view;
		this.message = message;
	}
	
	public void set(String message, COMMAND_TYPE command){
		this.message = message;
		this.command = command;
		this.view.update();
	}
	
	public void set(String message, int viewIndex, COMMAND_TYPE command){
		this.message = message;
		this.viewIndex = viewIndex;
		this.command = command;
		this.view.update();
	}
	
	public void set(View view, String message, int viewIndex, COMMAND_TYPE command){
		this.view = view;
		this.message = message;
		this.viewIndex = viewIndex;
		this.command = command;
	}

}
